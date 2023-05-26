package com.senla.authorization.controller.security;

import com.senla.common.constants.RolesAuthorities;
import com.senla.common.constants.UserRoles;
import com.senla.common.security.filters.JwtTokenSecurityCommonFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class AuthorizationWebSecurityConfig {
    private final JwtTokenSecurityCommonFilter commonJwtFilter = new JwtTokenSecurityCommonFilter(new String[] {
            "/authorization", "/authorization/register",
            "/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/error", "/favicon.ico"
    });

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .addFilterBefore(commonJwtFilter, UsernamePasswordAuthenticationFilter.class)
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
