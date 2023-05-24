package com.senla.common.exception.security.jwt.statuses;

import com.senla.common.exception.security.jwt.JwtTokenValidationException;

public class JwtTokenUnsupportedException extends JwtTokenValidationException {
    public JwtTokenUnsupportedException() {
    }

    public JwtTokenUnsupportedException(String s) {
        super(s);
    }

    public JwtTokenUnsupportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenUnsupportedException(Throwable cause) {
        super(cause);
    }
}
