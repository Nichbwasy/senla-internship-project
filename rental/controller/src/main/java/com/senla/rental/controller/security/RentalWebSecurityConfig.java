package com.senla.rental.controller.security;

import com.senla.common.constants.RolesAuthorities;
import com.senla.common.constants.UserRoles;
import com.senla.common.security.filters.JwtTokenSecurityCommonFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
public class RentalWebSecurityConfig {

    private final JwtTokenSecurityCommonFilter commonJwtFilter = new JwtTokenSecurityCommonFilter(new String[] {
            "/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/error", "/favicon.ico"
    });

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.addFilterBefore(commonJwtFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests( request -> request
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/error", "/favicon.ico")
                            .permitAll()
                        .requestMatchers(
                                "/profile/admin", "/profile/admin/**", "/profile/admin/requests","/profile/admin/requests/**",
                                "/blacklist/**", "/car/refunds/**",
                                "/requests", "/requests/**",
                                "/car/refunds/compensations/**", "/requests/**",
                                "/requests/rejections/**", "/requests/statuses/**")
                            .hasAuthority(UserRoles.ADMIN)
                        .requestMatchers(
                                "/catalog", "/catalog/request", "/catalog/request/**",
                                "/profile", "/profile/**")
                            .hasAuthority(UserRoles.USER)
                        .requestMatchers(HttpMethod.GET, "/car/refunds",
                                "/car/refunds/compensations", "/requests/rejections")
                            .hasAuthority(UserRoles.USER)
                        .anyRequest().authenticated()
                ).build();
    }
}
