package com.senla.starter.jwt.security.utils.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


public class JwtTokenUtils {
    private static SecretKey ACCESS_TOKEN_SECRET;
    private static SecretKey REFRESH_TOKEN_SECRET;

    public JwtTokenUtils(String accessTokenSecret, String refreshTokenSecret) {
        ACCESS_TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenSecret));
        REFRESH_TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshTokenSecret));
    }

    public Claims getAccessTokenClaims(@NonNull String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    public Long getAccessTokenUserId(@NonNull String accessToken) {
        return Long.parseLong(Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getId());
    }

    public String getAccessTokenUserLogin(@NonNull String accessToken) {
        return Jwts.parserBuilder()
                .setSigningKey(ACCESS_TOKEN_SECRET)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .getSubject();
    }

    public String getRefreshTokenUserLogin(@NonNull String refreshToken) {
        return Jwts.parserBuilder()
                .setSigningKey(REFRESH_TOKEN_SECRET)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody()
                .getSubject();
    }

}
