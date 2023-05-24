package com.senla.common.exception.security.jwt;

public class JwtTokenNotFoundException extends JwtTokenException {
    public JwtTokenNotFoundException() {
    }

    public JwtTokenNotFoundException(String s) {
        super(s);
    }

    public JwtTokenNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenNotFoundException(Throwable cause) {
        super(cause);
    }
}
