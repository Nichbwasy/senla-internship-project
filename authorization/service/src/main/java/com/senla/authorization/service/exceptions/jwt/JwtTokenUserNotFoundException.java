package com.senla.authorization.service.exceptions.jwt;

import com.senla.common.exception.security.jwt.JwtTokenException;

public class JwtTokenUserNotFoundException extends JwtTokenException {
    public JwtTokenUserNotFoundException() {
    }

    public JwtTokenUserNotFoundException(String s) {
        super(s);
    }

    public JwtTokenUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenUserNotFoundException(Throwable cause) {
        super(cause);
    }
}
