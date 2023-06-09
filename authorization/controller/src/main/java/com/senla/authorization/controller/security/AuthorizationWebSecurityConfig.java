package com.senla.authorization.controller.security;

import com.senla.common.clients.RefreshTokensMicroserviceClient;
import com.senla.common.constants.RolesAuthorities;
import com.senla.common.constants.UserRoles;
import com.senla.common.security.filters.JwtTokenSecurityCommonFilter;
import com.senla.starter.jwt.security.utils.authentication.JwtAuthenticationUtils;
import com.senla.starter.jwt.security.utils.utils.JwtTokenUtils;
import com.senla.starter.jwt.security.utils.validators.JwtTokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class AuthorizationWebSecurityConfig {

    @Bean
    public JwtTokenSecurityCommonFilter jwtTokenSecurityCommonFilter() {
        JwtTokenSecurityCommonFilter jwtTokenSecurityCommonFilter = new JwtTokenSecurityCommonFilter();
        jwtTokenSecurityCommonFilter.setIgnoredPaths(Arrays.asList(
                "/authorization", "/authorization/register",
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
        return httpSecurity
                .addFilterBefore(jwtTokenSecurityCommonFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests( request -> request
                        .requestMatchers("/authorization", "/authorization/register",
                                "/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/error", "/favicon.ico")
                            .permitAll()
                        .requestMatchers("/authorization/authorities", "/authorization/authorities/**")
                            .hasAnyAuthority(RolesAuthorities.AUTHORIZATION_AUTHORITIES_ACCESS, UserRoles.ADMIN)
                        .requestMatchers("/authorization/roles", "/authorization/roles/**")
                            .hasAnyAuthority(RolesAuthorities.AUTHORIZATION_ROLES_ACCESS, UserRoles.ADMIN)
                        .requestMatchers("/authorization/roles/authorities/adding", "/authorization/roles/authorities/removal")
                            .hasAuthority(UserRoles.ADMIN)
                        .requestMatchers("/api/tokens", "/users", "/users/**")
                            .hasAnyAuthority(UserRoles.MICROSERVICE, UserRoles.ADMIN)
                ).build();
    }

}
