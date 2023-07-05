package com.senla.authorization.service.exceptions.services.email;

public class UserWithEmailNotFoundException extends EmailServiceException {
    public UserWithEmailNotFoundException() {
    }

    public UserWithEmailNotFoundException(String message) {
        super(message);
    }

    public UserWithEmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserWithEmailNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserWithEmailNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
