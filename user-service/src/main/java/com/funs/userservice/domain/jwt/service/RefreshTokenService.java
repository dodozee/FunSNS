package com.funs.userservice.domain.jwt.service;


import com.funs.userservice.domain.jwt.exception.AccessTokenNotValidException;
import com.funs.userservice.domain.jwt.exception.RefreshTokenNotExistException;
import com.funs.userservice.domain.jwt.redis.RefreshToken;
import com.funs.userservice.domain.jwt.repository.RefreshTokenRedisRepository;
import com.funs.userservice.domain.user.dto.JwtTokenDto;
import com.funs.userservice.domain.user.entity.User;
import com.funs.userservice.domain.user.exception.NotExistUserException;
import com.funs.userservice.domain.user.repository.UserRepository;
import com.funs.userservice.global.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RefreshTokenService {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;

    @Transactional
    public void updateRefreshToken(Long id, String uuid){
        User user = userRepository.findById(id)
            .orElseThrow(() -> new NotExistUserException("사용자 고유번호 : " + id + "는 없는 사용자입니다."));

        refreshTokenRedisRepository.save(RefreshToken.of(user.getId().toString(), uuid));
    }

    @Transactional
    public JwtTokenDto refreshJwtToken(String accessToken, String refreshToken) {
        String userId = jwtTokenProvider.getUserId(accessToken);

        RefreshToken findRefreshToken = refreshTokenRedisRepository.findById(userId)
                .orElseThrow(()->new RefreshTokenNotExistException("사용자 고유번호 : " + userId + "는 등록된 리프레쉬 토큰이 없습니다."));


        //refresh token 검증
        String findRefreshTokenId = findRefreshToken.getRefreshTokenId();
        if(!jwtTokenProvider.validateJwtToken(refreshToken)){
            refreshTokenRedisRepository.delete(findRefreshToken);
            throw new RefreshTokenNotExistException("Not validate jwt token = " + refreshToken);
        }

        if (!jwtTokenProvider.equalRefreshTokenId(findRefreshTokenId, refreshToken)) {
            throw new RefreshTokenNotExistException("요청의 refresh token와 redis의 refresh token이 다릅니다." + refreshToken);
        }

        User findUser  = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new NotExistUserException("사용자 고유번호 : " + userId + "는 없는 사용자입니다."));

        //access token 재발급
        Authentication authentication = getAuthentication(findUser.getEmail());
        //이해해야함
        List<String> roles = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String newAccessToken = jwtTokenProvider.createJwtAccessToken(userId, "/reissu", roles);
        Date expiredTime = jwtTokenProvider.getExpiredTime(newAccessToken);

        return JwtTokenDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiredDate(expiredTime)
                .build();

    }



    public void logoutToken(String accessToken){
        if (!jwtTokenProvider.validateJwtToken(accessToken)) {
            throw new AccessTokenNotValidException("Not validate jwt token = " + accessToken);
        }

        System.out.println("logoutToken 메서드에서 ValidateJwtToken 까지는 통과: ");
        System.out.println("jwtTokenProvider.getUserId(accessToken) 값 :" + jwtTokenProvider.getUserId(accessToken));
        RefreshToken refreshToken = refreshTokenRedisRepository.findById(jwtTokenProvider.getUserId(accessToken))
                .orElseThrow(() -> new RefreshTokenNotExistException("refresh 고유번호 : " + jwtTokenProvider.getUserId(accessToken) + "는 등록된 리프레쉬 토큰이 없습니다."));

        //com.withsport.userservice.domain.jwt.redis.RefreshToken@784ff00e
        System.out.println("refreshToken"+refreshToken);
        refreshTokenRedisRepository.delete(refreshToken);
    }

    public Authentication getAuthentication(String email){
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

    }



}
