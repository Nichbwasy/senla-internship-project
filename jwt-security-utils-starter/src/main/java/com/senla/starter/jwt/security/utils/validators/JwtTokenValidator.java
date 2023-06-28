package com.senla.starter.jwt.security.utils.validators;

import com.senla.starter.jwt.security.utils.consts.TokenStatus;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;

@Slf4j
public class JwtTokenValidator {

    private static SecretKey ACCESS_TOKEN_SECRET;
    private static SecretKey REFRESH_TOKEN_SECRET;

    public JwtTokenValidator(String accessTokenSecret, String refreshTokenSecret) {
        ACCESS_TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenSecret));
        REFRESH_TOKEN_SECRET = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshTokenSecret));
    }

    public TokenStatus validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, ACCESS_TOKEN_SECRET);
    }

    public TokenStatus validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, REFRESH_TOKEN_SECRET);
    }

    private TokenStatus validateToken(String token, SecretKey secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            log.info("Token is valid. {}", token);
            return TokenStatus.OK;
        } catch (ExpiredJwtException expEx) {
            log.warn("Token is expired. {}", token);
            return TokenStatus.EXPIRED;
        } catch (UnsupportedJwtException unsEx) {
            log.warn("Token is unsupported. {}", token);
            return TokenStatus.UNSUPPORTED;
        } catch (MalformedJwtException mjEx) {
            log.warn("Token is malformed. {}", token);
            return TokenStatus.MALFORMED;
        } catch (SignatureException sEx) {
            log.warn("Token has a wrong signature. {}", token);
            return TokenStatus.WRONG_SIGNATURE;
        } catch (Exception e) {
            log.warn("Token invalid. {}", token);
            return TokenStatus.INVALID;
        }
    }

}
