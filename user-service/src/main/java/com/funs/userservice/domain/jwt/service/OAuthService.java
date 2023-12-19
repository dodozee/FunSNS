package com.funs.userservice.domain.jwt.service;

import com.funs.userservice.domain.user.dto.OAuthAttributeDto;
import com.funs.userservice.domain.user.entity.User;
import com.funs.userservice.domain.user.repository.UserRepository;
import com.funs.userservice.domain.user.service.UserServiceImpl;
import com.funs.userservice.global.utils.CookieProvider;
import com.funs.userservice.global.utils.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserServiceImpl userServiceImpl;
    private final UserRepository userRepository;
    private final CookieProvider cookieProvider;

    /**
     * OAuth2UserService의 OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) 메서드는
     * 서드파티에 사용자 정보를 요청할 수 있는 access token 을 얻고나서 실행된다.
     */

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        /**
         * DefaultOAuth2UserService 객체를 생성하여, loadUser(userRequest)를 통해 DefaultOAuth2User 객체를 생성 후 반환
         * DefaultOAuth2UserService의 loadUser()는 소셜 로그인 API의 사용자 정보 제공 URI로 요청을 보내서
         * 사용자 정보를 얻은 후, 이를 통해 DefaultOAuth2User 객체를 생성 후 반환한다.
         * 결과적으로, oAuth2User는 OAuth 서비스에서 가져온 유저 정보를 담고 있는 유저
         */

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        //access token을 이용해 서드파티 서버로부터 사용자 정보를 받아온다
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        /**
         * userRequest에서 registrationId 추출 후 registrationId으로 SocialType 저장
         * http://localhost:8080/oauth2/authorization/kakao에서 kakao가 registrationId
         * userNameAttributeName은 이후에 nameAttributeKey로 설정된다.
         */
        //OAuth 서비스의 구분 id ex) naver
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        System.out.println("=======================registrationId = " + registrationId);
        //OAuth 로그인 진행시 키가 되는 필드값
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); // OAuth2 로그인 시 키(PK)가 되는 필드값

        // socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
        OAuthAttributeDto attributeDto = OAuthAttributeDto.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        //사용자 저장
        User user = saveOrUpdateUser(attributeDto);
        user.updateLoginCount();

        String userEmail = user.getEmail();

        Collection<? extends GrantedAuthority> authorities = userServiceImpl.loadUserByUsername(userEmail).getAuthorities();
        return new DefaultOAuth2User(authorities, attributeDto.getAttributes(), attributeDto.getNameAttributeKey());
    }


    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String userEmail = String.valueOf(((DefaultOAuth2User) authentication.getPrincipal()).getAttributes().get("email"));


        String refreshToken = jwtTokenProvider.createJwtRefreshToken();
        Long userId = userRepository.findByEmail(userEmail).get().getId();
        refreshTokenService.updateRefreshToken(userId, jwtTokenProvider.getRefreshTokenId(refreshToken));

        User user = userRepository.findById(userId).get();
        Long loginCount = user.getLoginCount();
        boolean isFirstLogin = true;
        if (loginCount != 1) {
            isFirstLogin = false;
        }

        // 쿠키 설정 - refresh Token 저장
        ResponseCookie refreshTokenCookie = cookieProvider.createRefreshTokenCookie(refreshToken);

        Cookie cookie = cookieProvider.of(refreshTokenCookie);
        cookie.setDomain("funs.vercel.app");

        response.setContentType(APPLICATION_JSON_VALUE);
        response.addCookie(cookie);



        //쿼리 파라미터에 access Token 저장
        String accessToken = jwtTokenProvider.createJwtAccessToken(String.valueOf(userId), request.getRequestURI(),
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()));
        Date expiredTime = jwtTokenProvider.getExpiredTime(accessToken);
//        //프론트 서버로 보낼 send Redirect URI를 지정. (프론트 서버에서는 쿠키에 저장된 refreshToken과
//        URI의 쿼리 파라미터로 accessToken, 토큰 만료시간을 받는다.)
//        response.sendRedirect("http://localhost:8080/user/"+userId);


        System.out.println("리다이렉트 메서드 실행");
        response.sendRedirect("https://funs.vercel.app/auth?" +
                "accessToken=" + accessToken +
                "&expiredTime="+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expiredTime) + "&userId=" + userId+ "&isFirstLogin=" + isFirstLogin);

    }


    //최초 로그인인 시 회원가입을 진행한다.
    //이미 정보가 있을경우 정보들을  업데이트 한다.
    @Transactional
    public User saveOrUpdateUser(OAuthAttributeDto attributeDto) {
        return userRepository.save(
                userRepository.findByEmail(attributeDto.getEmail())
                        .orElse(attributeDto.toEntity(attributeDto)));


    }
}
