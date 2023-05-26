package com.senla.car.run.config;

import com.senla.common.constants.UserRoles;
import com.senla.common.security.filters.JwtTokenSecurityCommonFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class CarWebSecurityConfig {

    private final JwtTokenSecurityCommonFilter jwtTokenFilter = new JwtTokenSecurityCommonFilter(new String[] {
            "/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/error", "/favicon.ico"
    });

    // Needs for springdoc openapi (swagger)
    @Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests((request) -> request
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/error", "/favicon.ico")
                            .permitAll()
                        .anyRequest().hasAnyAuthority(UserRoles.ADMIN, UserRoles.MICROSERVICE)
                ).build();
    }

}
