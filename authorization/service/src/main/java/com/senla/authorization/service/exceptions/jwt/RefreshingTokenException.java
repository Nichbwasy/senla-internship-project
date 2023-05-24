package com.senla.authorization.service.exceptions.jwt;


import com.senla.common.exception.security.jwt.JwtTokenException;

public class RefreshingTokenException extends JwtTokenException {

    public RefreshingTokenException() {
    }

    public RefreshingTokenException(String s) {
        super(s);
    }

    public RefreshingTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshingTokenException(Throwable cause) {
        super(cause);
    }
}
