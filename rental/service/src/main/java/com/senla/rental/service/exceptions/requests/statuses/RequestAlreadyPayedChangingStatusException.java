package com.senla.rental.service.exceptions.requests.statuses;

public class RequestAlreadyPayedChangingStatusException extends RequestStatusChangingException{

    public RequestAlreadyPayedChangingStatusException() {
    }

    public RequestAlreadyPayedChangingStatusException(String message) {
        super(message);
    }

    public RequestAlreadyPayedChangingStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestAlreadyPayedChangingStatusException(Throwable cause) {
        super(cause);
    }

    public RequestAlreadyPayedChangingStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
