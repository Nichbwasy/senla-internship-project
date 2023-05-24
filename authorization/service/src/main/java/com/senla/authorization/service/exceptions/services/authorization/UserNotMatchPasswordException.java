package com.senla.authorization.service.exceptions.services.authorization;

public class UserNotMatchPasswordException extends AuthorizationException {
    public UserNotMatchPasswordException() {
    }

    public UserNotMatchPasswordException(String message) {
        super(message);
    }

    public UserNotMatchPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotMatchPasswordException(Throwable cause) {
        super(cause);
    }

    public UserNotMatchPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
