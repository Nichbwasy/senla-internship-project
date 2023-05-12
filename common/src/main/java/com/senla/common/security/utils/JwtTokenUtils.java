package com.senla.common.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


@Component
public class JwtTokenUtils {
    private static SecretKey ACCESS_TOKEN_SECRET;
    private static SecretKey REFRESH_TOKEN_SECRET;

    public JwtTokenUtils(
            @Value("${security.access.token.secret}") String accessTokenSecret,
            @Value("${security.refresh.token.secret}") String refreshTokenSecret
    ) {
        ACCESS_TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenSecret));
        REFRESH_TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshTokenSecret));
    }

    public static Claims getAccessTokenClaims(@NonNull String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    public static Long getAccessTokenUserId(@NonNull String accessToken) {
        return Long.parseLong(Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getId());
    }

    public static String getAccessTokenUserLogin(@NonNull String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }

    public static String getRefreshTokenUserLogin(@NonNull String refreshToken) {
        return Jwts.parserBuilder()
                .setSigningKey(REFRESH_TOKEN_SECRET)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody()
                .getSubject();
    }

}
