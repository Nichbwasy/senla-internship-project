package com.senla.rental.service.exceptions.requests.statuses;

public class RequestAlreadyCanceledChangingStatusException extends RequestStatusChangingException {

    public RequestAlreadyCanceledChangingStatusException() {
    }

    public RequestAlreadyCanceledChangingStatusException(String message) {
        super(message);
    }

    public RequestAlreadyCanceledChangingStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestAlreadyCanceledChangingStatusException(Throwable cause) {
        super(cause);
    }

    public RequestAlreadyCanceledChangingStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
