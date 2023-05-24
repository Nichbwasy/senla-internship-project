package com.senla.common.security.authentication;

import com.senla.common.security.dto.JwtAuthenticationDto;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Slf4j
public class JwtAuthenticationUtils {

    public static JwtAuthenticationDto generateJwtAuthentication(Claims claims) {
        JwtAuthenticationDto jwtInfoToken = new JwtAuthenticationDto();
        jwtInfoToken.setAuthorities(getAuthorities(claims));
        jwtInfoToken.setUsername(claims.getSubject());
        jwtInfoToken.setAuthenticated(true);
        log.debug("A new authentication has been generated.");
        return jwtInfoToken;
    }

    private static Collection<SimpleGrantedAuthority> getAuthorities(Claims claims) {
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        List<String> roles = claims.get("authorities", List.class);
        roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));
        log.debug("Simple grand authorities has been got from claims.");
        return authorities;
    }

}
