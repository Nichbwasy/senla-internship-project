package com.senla.rental.service.exceptions.profiles;

public class NoNeedCompensationProfileException extends UserProfileServiceException {
    public NoNeedCompensationProfileException() {
    }

    public NoNeedCompensationProfileException(String message) {
        super(message);
    }

    public NoNeedCompensationProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoNeedCompensationProfileException(Throwable cause) {
        super(cause);
    }

    public NoNeedCompensationProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
