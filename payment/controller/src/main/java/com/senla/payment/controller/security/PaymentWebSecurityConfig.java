package com.senla.payment.controller.security;

import com.senla.common.clients.RefreshTokensMicroserviceClient;
import com.senla.common.constants.UserRoles;
import com.senla.common.security.filters.JwtTokenSecurityCommonFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class PaymentWebSecurityConfig {

    @Bean
    public JwtTokenSecurityCommonFilter jwtTokenSecurityCommonFilter() {
        JwtTokenSecurityCommonFilter jwtTokenSecurityCommonFilter = new JwtTokenSecurityCommonFilter();
        jwtTokenSecurityCommonFilter.setIgnoredPaths(Arrays.asList(
                "/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/error", "/favicon.ico"
        ));
        return jwtTokenSecurityCommonFilter;
    }

    @Bean
    public RefreshTokensMicroserviceClient refreshTokensMicroserviceClient(
            @Value("${microservice.name}") String microserviceName,
            @Value("${authorization.microservice.url}") String microserviceUrl) {
        return new RefreshTokensMicroserviceClient(microserviceName, microserviceUrl);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.addFilterBefore(jwtTokenSecurityCommonFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests( request -> request
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/error", "/favicon.ico")
                        .permitAll()
                        .requestMatchers(
                                "/receipts/**",
                                "/payment", "/payment/**"
                        )
                        .hasAnyAuthority(UserRoles.ADMIN, UserRoles.MICROSERVICE)
                        .anyRequest().authenticated()
                ).build();
    }

}
