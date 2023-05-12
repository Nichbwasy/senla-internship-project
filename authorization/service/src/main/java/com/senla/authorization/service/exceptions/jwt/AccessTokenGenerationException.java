package com.senla.authorization.service.exceptions.jwt;


import com.senla.common.exception.security.jwt.JwtTokenException;

public class AccessTokenGenerationException extends JwtTokenException {

    public AccessTokenGenerationException() {
    }

    public AccessTokenGenerationException(String message) {
        super(message);
    }

    public AccessTokenGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessTokenGenerationException(Throwable cause) {
        super(cause);
    }

}
