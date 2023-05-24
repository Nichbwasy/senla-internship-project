package com.senla.authorization.service.exceptions.services;

public class AuthorizationServicesException extends RuntimeException {
    public AuthorizationServicesException() {
    }

    public AuthorizationServicesException(String message) {
        super(message);
    }

    public AuthorizationServicesException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationServicesException(Throwable cause) {
        super(cause);
    }

    public AuthorizationServicesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
