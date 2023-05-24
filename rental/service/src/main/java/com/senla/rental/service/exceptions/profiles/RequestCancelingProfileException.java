package com.senla.rental.service.exceptions.profiles;

public class RequestCancelingProfileException extends UserProfileServiceException {
    public RequestCancelingProfileException() {
    }

    public RequestCancelingProfileException(String message) {
        super(message);
    }

    public RequestCancelingProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestCancelingProfileException(Throwable cause) {
        super(cause);
    }

    public RequestCancelingProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
