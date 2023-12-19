package com.funs.userservice.domain.jwt.web;


import com.funs.userservice.domain.jwt.service.AccessTokenService;
import com.funs.userservice.domain.jwt.service.RefreshTokenService;
import com.funs.userservice.domain.user.dto.JwtTokenDto;
import com.funs.userservice.domain.user.service.UserService;
import com.funs.userservice.domain.jwt.web.response.RefreshTokenResponse;
import com.funs.userservice.global.dto.Result;
import com.funs.userservice.global.utils.CookieProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthApi {

    private final RefreshTokenService refreshTokenService;
    private final AccessTokenService accessTokenService;
    private final CookieProvider cookieProvider;
    private final UserService userService;

    //X-AUTH-TOKEN
    @GetMapping("/reissue")
    public ResponseEntity<Result> refreshToken(@RequestHeader(value="Authorization") String accessToken,
                                               @CookieValue(value="refresh-token") String refreshToken) {


        JwtTokenDto jwtTokenDto = refreshTokenService.refreshJwtToken(accessToken, refreshToken);

        ResponseCookie responseCookie = cookieProvider.createRefreshTokenCookie(refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(Result.createSuccessResult(new RefreshTokenResponse(jwtTokenDto)));
    }

    @PostMapping("/logout")
    public ResponseEntity<Result> logout(@Valid @RequestHeader("Authorization") String accessToken){
        refreshTokenService.logoutToken(accessToken);


        System.out.println("로그아웃 api 작동");
        ResponseCookie responseCookie = cookieProvider.deleteRefreshTokenCookie();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(Result.createSuccessResult("로그아웃이 성공적으로 되었습니다."));
    }

    @GetMapping(value = "/check/access-token")
    public ResponseEntity<Result> checkAccessToken(@Valid @RequestHeader(name = "Authorization") String authorization) {
        System.out.println("999999999999999");
        accessTokenService.checkAccessToken(authorization);

        return ResponseEntity.status(HttpStatus.OK)
                .body(Result.createSuccessResult(null));
    }






}
