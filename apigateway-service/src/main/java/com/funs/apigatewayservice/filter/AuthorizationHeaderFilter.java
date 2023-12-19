package com.funs.apigatewayservice.filter;


import com.funs.apigatewayservice.security.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private final JwtTokenProvider jwtTokenProvider;
    static class Config {

    }
    @Autowired
    public AuthorizationHeaderFilter(JwtTokenProvider jwtTokenProvider) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            System.out.println("11111111111");
            ServerHttpRequest request = exchange.getRequest();

            HttpHeaders headers = request.getHeaders();
            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                System.out.println("1212121212121212");
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }
            System.out.println("22222222222222");
            String authorizationHeader = headers.get(HttpHeaders.AUTHORIZATION).get(0);

            //JWT 토큰 판별

            String token = authorizationHeader.replace("Bearer ", "");
            System.out.println("제발 여기여라..");
            System.out.println("token = " + token);
            jwtTokenProvider.validateJwtToken(token);

            String subject = jwtTokenProvider.getUserId(token);
            System.out.println("33333333333");
            if(subject.equals("feign"))
                return chain.filter(exchange);

            if(!jwtTokenProvider.getRoles(token).contains("User")){
                return onError(exchange, "권한 없음", HttpStatus.UNAUTHORIZED);
            }
            System.out.println("헤더에 유저 아이디 넣기 직전");

            //헤더에 유저 아이디 추가
            ServerHttpRequest newRequest = request.mutate()
                    .header("USER-ID", subject)
                    .build();
            System.out.println("헤더에 유저 아이디 넣고 난 후");


            return chain.filter(exchange.mutate().request(newRequest).build());
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMsg, HttpStatus httpStatus) {
        log.error("errorMsg {} ", errorMsg);

        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }



}

