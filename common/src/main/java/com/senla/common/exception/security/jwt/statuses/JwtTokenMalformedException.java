package com.senla.common.exception.security.jwt.statuses;

import com.senla.common.exception.security.jwt.JwtTokenValidationException;

public class JwtTokenMalformedException extends JwtTokenValidationException {
    public JwtTokenMalformedException() {
    }

    public JwtTokenMalformedException(String s) {
        super(s);
    }

    public JwtTokenMalformedException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenMalformedException(Throwable cause) {
        super(cause);
    }
}
