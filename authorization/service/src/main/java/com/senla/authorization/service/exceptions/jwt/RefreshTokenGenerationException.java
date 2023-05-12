package com.senla.authorization.service.exceptions.jwt;

import com.senla.common.exception.security.jwt.JwtTokenException;

public class RefreshTokenGenerationException extends JwtTokenException {

    public RefreshTokenGenerationException() {
    }

    public RefreshTokenGenerationException(String s) {
        super(s);
    }

    public RefreshTokenGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenGenerationException(Throwable cause) {
        super(cause);
    }
}
