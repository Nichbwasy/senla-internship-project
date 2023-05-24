package com.senla.common.exception.security.jwt.statuses;

import com.senla.common.exception.security.jwt.JwtTokenValidationException;

public class JwtTokenSignatureException extends JwtTokenValidationException {
    public JwtTokenSignatureException() {
    }

    public JwtTokenSignatureException(String s) {
        super(s);
    }

    public JwtTokenSignatureException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenSignatureException(Throwable cause) {
        super(cause);
    }
}
