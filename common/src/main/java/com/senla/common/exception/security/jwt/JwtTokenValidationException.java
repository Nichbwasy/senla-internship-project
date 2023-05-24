package com.senla.common.exception.security.jwt;

public class JwtTokenValidationException extends JwtTokenException {
    public JwtTokenValidationException() {
    }

    public JwtTokenValidationException(String s) {
        super(s);
    }

    public JwtTokenValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenValidationException(Throwable cause) {
        super(cause);
    }
}
