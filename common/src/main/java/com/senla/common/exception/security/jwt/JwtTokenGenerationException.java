package com.senla.common.exception.security.jwt;

public class JwtTokenGenerationException extends JwtTokenException {
    public JwtTokenGenerationException() {
    }

    public JwtTokenGenerationException(String s) {
        super(s);
    }

    public JwtTokenGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
    public JwtTokenGenerationException(Throwable cause) {
        super(cause);
    }
}
