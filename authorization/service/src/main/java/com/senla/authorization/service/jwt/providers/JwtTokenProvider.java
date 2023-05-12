package com.senla.authorization.service.jwt.providers;

import com.senla.authorization.model.Authority;
import com.senla.authorization.model.UserData;
import com.senla.authorization.service.exceptions.jwt.AccessTokenGenerationException;
import com.senla.authorization.service.exceptions.jwt.RefreshTokenGenerationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${security.access.token.secret}")
    private String accessTokenSecret;
    @Value("${security.refresh.token.secret}")
    private String refreshTokenSecret;
    @Value("${security.access.token.lifetime}")
    private Integer ACCESS_TOKEN_LIFETIME;
    @Value("${security.refresh.token.lifetime}")
    private Integer REFRESH_TOKEN_LIFETIME;
    private static SecretKey ACCESS_TOKEN_SECRET;
    private static SecretKey REFRESH_TOKEN_SECRET;

    public JwtTokenProvider()
    {
        log.info("Initializing the JWT token provider...");
        ACCESS_TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenSecret));
        REFRESH_TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshTokenSecret));
    }

    public String generateAccessToken(@NotNull UserData userData) {
        try {
            log.info("Generating a access token for the '{}' user...", userData.getLogin());
            LocalDateTime now = LocalDateTime.now();
            Instant timeInstant = now.plusSeconds(ACCESS_TOKEN_LIFETIME)
                    .atZone(ZoneId.systemDefault()).toInstant();
            Date expiration = Date.from(timeInstant);
            Set<String> authorities = new HashSet<>();
            userData.getRoles().forEach(r -> {
                        authorities.addAll(
                                r.getAuthorities().stream()
                                        .map(Authority::getName)
                                        .collect(Collectors.toSet()));
                        authorities.add(r.getName());
            });
            return Jwts.builder()
                    .setSubject(userData.getLogin())
                    .setExpiration(expiration)
                    .signWith(ACCESS_TOKEN_SECRET, SignatureAlgorithm.HS512)
                    .claim("authorities", authorities)
                    .setId(userData.getId().toString())
                    .compact();
        } catch (Exception e) {
            log.error("Exception while generating access token for user '{}'! {}", userData.getLogin(), e.getMessage());
            throw new AccessTokenGenerationException(
                    String.format("Exception while generating access token for user '%s'! %s",
                            userData.getLogin(), e.getMessage())
            );
        }
    }

    public String generateRefreshToken(@NotNull UserData userData) {
        try {
            log.info("Generating a refresh token for the '{}' user...", userData.getLogin());
            LocalDateTime now = LocalDateTime.now();
            Instant timeInstant = now.plusSeconds(REFRESH_TOKEN_LIFETIME)
                    .atZone(ZoneId.systemDefault()).toInstant();
            Date expiration = Date.from(timeInstant);
            return Jwts.builder()
                    .setSubject(userData.getLogin())
                    .setExpiration(expiration)
                    .signWith(REFRESH_TOKEN_SECRET, SignatureAlgorithm.HS512)
                    .compact();
        } catch (Exception e) {
            log.error("Exception while generating refresh token for user '{}'! {}",
                    userData.getLogin(), e.getMessage());
            throw new RefreshTokenGenerationException(
                    String.format("Exception while generating refresh token for user '%s'! %s",
                            userData.getLogin(), e.getMessage())
            );
        }
    }


}
