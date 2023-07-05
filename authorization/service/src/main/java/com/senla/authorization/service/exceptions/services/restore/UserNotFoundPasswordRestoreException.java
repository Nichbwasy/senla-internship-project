package com.senla.authorization.service.exceptions.services.restore;

public class UserNotFoundPasswordRestoreException extends PasswordRestoreServiceException {
    public UserNotFoundPasswordRestoreException() {
    }

    public UserNotFoundPasswordRestoreException(String message) {
        super(message);
    }

    public UserNotFoundPasswordRestoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundPasswordRestoreException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundPasswordRestoreException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
