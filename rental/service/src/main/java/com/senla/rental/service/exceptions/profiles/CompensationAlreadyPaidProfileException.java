package com.senla.rental.service.exceptions.profiles;

public class CompensationAlreadyPaidProfileException extends UserProfileServiceException {
    public CompensationAlreadyPaidProfileException() {
    }

    public CompensationAlreadyPaidProfileException(String message) {
        super(message);
    }

    public CompensationAlreadyPaidProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompensationAlreadyPaidProfileException(Throwable cause) {
        super(cause);
    }

    public CompensationAlreadyPaidProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
