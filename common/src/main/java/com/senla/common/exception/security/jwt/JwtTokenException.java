package com.senla.common.exception.security.jwt;

import com.senla.common.exception.security.SecurityCommonException;

public class JwtTokenException extends SecurityCommonException {
    public JwtTokenException() {
    }

    public JwtTokenException(String s) {
        super(s);
    }

    public JwtTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenException(Throwable cause) {
        super(cause);
    }
}
