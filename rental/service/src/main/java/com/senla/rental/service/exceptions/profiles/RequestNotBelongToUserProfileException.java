package com.senla.rental.service.exceptions.profiles;

public class RequestNotBelongToUserProfileException extends UserProfileServiceException{
    public RequestNotBelongToUserProfileException() {
    }

    public RequestNotBelongToUserProfileException(String message) {
        super(message);
    }

    public RequestNotBelongToUserProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestNotBelongToUserProfileException(Throwable cause) {
        super(cause);
    }

    public RequestNotBelongToUserProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
