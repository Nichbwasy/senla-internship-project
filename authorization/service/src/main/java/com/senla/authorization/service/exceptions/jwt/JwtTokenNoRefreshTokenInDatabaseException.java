package com.senla.authorization.service.exceptions.jwt;

public class JwtTokenNoRefreshTokenInDatabaseException extends RefreshingTokenException {
    public JwtTokenNoRefreshTokenInDatabaseException() {
    }

    public JwtTokenNoRefreshTokenInDatabaseException(String s) {
        super(s);
    }

    public JwtTokenNoRefreshTokenInDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenNoRefreshTokenInDatabaseException(Throwable cause) {
        super(cause);
    }
}
