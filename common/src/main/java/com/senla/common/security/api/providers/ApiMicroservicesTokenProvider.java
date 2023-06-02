
package com.senla.common.security.api.providers;

import com.senla.common.constants.UserRoles;
import com.senla.common.exception.security.jwt.JwtTokenGenerationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Slf4j
@Component
public class ApiMicroservicesTokenProvider {

    private final static Integer ACCESS_TOKEN_LIFETIME = 60;
    private static SecretKey ACCESS_TOKEN_SECRET;
    private static String MICROSERVICE_NAME;

    public ApiMicroservicesTokenProvider(
            @Value("${spring.senla.jwt.utils.access-token-secret}") String accessTokenSecret,
            @Value("${microservice.name}") String microserviceName)
    {
        ACCESS_TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenSecret));
        MICROSERVICE_NAME = microserviceName;
    }

    public static String generateMicroserviceAccessToken() {
        try {
            LocalDateTime now = LocalDateTime.now();
            Instant timeInstant = now.plusSeconds(ACCESS_TOKEN_LIFETIME)
                    .atZone(ZoneId.systemDefault()).toInstant();
            Date expiration = Date.from(timeInstant);
            Set<String> authorities = Collections.singleton(UserRoles.MICROSERVICE);
            return Jwts.builder()
                    .setSubject(MICROSERVICE_NAME)
                    .setExpiration(expiration)
                    .signWith(ACCESS_TOKEN_SECRET, SignatureAlgorithm.HS512)
                    .claim("authorities", authorities)
                    .compact();
        } catch (Exception e) {
            log.error("Exception while generating access token for microservice '{}'! {}", MICROSERVICE_NAME, e.getMessage());
            throw new JwtTokenGenerationException(
                    String.format("Exception while generating access token for microservice '%s'! %s",
                            MICROSERVICE_NAME, e.getMessage())
            );
        }
    }

}
