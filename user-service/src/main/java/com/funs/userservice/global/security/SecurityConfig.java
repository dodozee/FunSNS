package com.funs.userservice.global.security;

import com.funs.userservice.domain.jwt.service.OAuthService;
import com.funs.userservice.domain.jwt.service.RefreshTokenService;
import com.funs.userservice.global.utils.CookieProvider;
import com.funs.userservice.global.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final CookieProvider cookieProvider;


    private final OAuthService oAuthService;

    private final ObjectPostProcessor<Object> objectPostProcessor;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable();

//        http.headers()
//                .frameOptions()
//                .disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeHttpRequests().anyRequest().permitAll();
        http.logout()
                .logoutUrl("/logout")
                .deleteCookies("refresh-token");

//       http.csrf(AbstractHttpConfigurer::disable)
//               .sessionManagement((sessionManagement) ->
//                       sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                ).authorizeHttpRequests((authorizeRequests) ->
//                       authorizeRequests.anyRequest().permitAll()
//               ).logout(withDefaults());
////        http.logout()
////                .logoutUrl("/logout")
////                .deleteCookies("refresh-token");


        System.out.println("여기까지 실행되는건가?제발 살려주세요...");
        //oauth2Login 설정 시작


//        http.oauth2Login(oauth2 -> oauth2.userInfoEndpoint(
//                userInfoEndpointConfig -> userInfoEndpointConfig
//                        .userService(oAuthService)
//                        .successHandler(oAuthService::onAuthenticationSuccess)));
        http.oauth2Login()
                // oauth2Login 성공 이후의 설정을 시작
                .userInfoEndpoint()
                //userService에서 처리하겠다는 설정
                .userService(oAuthService)
                .and()
                .failureUrl("http://funs.com/login")
                .successHandler(oAuthService::onAuthenticationSuccess);

        http.addFilter(getAuthenticationFilter());
        http.addFilterBefore(new HeaderAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);


        System.out.println("여기까지 실행되는건가?");
        return http.build();
    }


    private LoginAuthenticationFilter getAuthenticationFilter() throws Exception {
        System.out.println("여기까지 실행되는건가?2");
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(jwtTokenProvider, refreshTokenService, cookieProvider);
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
        loginAuthenticationFilter.setAuthenticationManager(authenticationManager(builder));
        loginAuthenticationFilter.setFilterProcessesUrl("/login");
        return loginAuthenticationFilter;
    }

    public AuthenticationManager authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return auth.build();
    }


}

/**
 *  @Bean
 *     protected SecurityFilterChain config(HttpSecurity http) throws Exception {
 *         http.csrf().disable();
 *         http.headers().frameOptions().disable();
 *         http.authorizeHttpRequests(authorize -> {
 *                     try {
 *                         authorize
 *                                 .requestMatchers(WHITE_LIST).permitAll()
 *                                 .requestMatchers(PathRequest.toH2Console()).permitAll()
 *                                 .requestMatchers(new IpAddressMatcher("127.0.0.1")).permitAll()
 *                                 .and()
 *                                 .addFilter(getAuthenticationFilter());
 *                     } catch (Exception e) {
 *                         e.printStackTrace();
 *                     }
 *                 }
 *         );
 *         return http.build();
 *     }
 * @param http
 * @return
 * @throws Exception
 */
