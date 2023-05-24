package com.senla.authorization.service.exceptions.jwt;

import com.senla.common.exception.security.jwt.JwtTokenException;

public class JwtTokenMatchException extends JwtTokenException {
    public JwtTokenMatchException() {
    }

    public JwtTokenMatchException(String s) {
        super(s);
    }

    public JwtTokenMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenMatchException(Throwable cause) {
        super(cause);
    }
}
