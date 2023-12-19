package com.funs.userservice.global.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${token.access-expired-time}")
    private long ACCESS_EXPIRED_TIME;

    @Value("${token.refresh-expired-time}")
    private long REFRESH_EXPIRED_TIME;

    @Value("${token.secret}")
    private String SECRET;

    public String createJwtAccessToken(String userId, String uri, List<String> roles){
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("roles", roles);

        return Jwts.builder()
                .addClaims(claims)
                .setExpiration(
                        new Date(System.currentTimeMillis() + ACCESS_EXPIRED_TIME)
                )
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuer(uri)
                .compact();
    }

    public String createJwtRefreshToken() {
        Claims claims = Jwts.claims();
        claims.put("value", UUID.randomUUID());

        return Jwts.builder()
                .addClaims(claims)
                .setExpiration(
                        new Date(System.currentTimeMillis() + REFRESH_EXPIRED_TIME)
                )
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public boolean validateJwtToken(String authorizationHeader) {
        System.out.println("validateJwtToken: " + authorizationHeader);
        String token = getAccessToken(authorizationHeader);
        System.out.println("token: " + token);
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            return false;
        }
    }

    public boolean equalRefreshTokenId(String refreshTokenId, String refreshToken){
        return refreshTokenId.equals(getRefreshTokenId(refreshToken));
    }

    //access토큰으로 부터 userId를 추출
    public String getUserId(String accessToken){
        System.out.println("여기까지 실행 : getUserId");
        String token = getAccessToken(accessToken);
        return getClaimsFromJwtToken(token).getSubject();
    }

    private Claims getClaimsFromJwtToken(String token){
        try{
            System.out.println("여기까지 실행 : getClaimsFromJwtToken");
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            log.error("getClaimsFromJwtToken error : {}", e.getMessage());
            throw new RuntimeException("getClaimsFromJwtToken error : " + e.getMessage());
        }
    }

    private static String getAccessToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return token;
    }



    public String getRefreshTokenId(String token){
        return getClaimsFromJwtToken(token).get("value").toString();

    }

    public Date getExpiredTime(String token){
        return getClaimsFromJwtToken(token).getExpiration();
    }

    public List<String> getRoles(String token){
        return (List<String>) getClaimsFromJwtToken(token).get("roles");
    }




}
