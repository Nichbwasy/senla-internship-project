package com.senla.rental.service.exceptions.profiles;

public class UserNotFoundProfileException extends UserProfileServiceException {
    public UserNotFoundProfileException() {
    }

    public UserNotFoundProfileException(String message) {
        super(message);
    }

    public UserNotFoundProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundProfileException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
