package com.senla.rental.service.exceptions.requests.statuses;

public class RequestDeniedChangingStatusException extends RequestStatusChangingException {

    public RequestDeniedChangingStatusException() {
    }

    public RequestDeniedChangingStatusException(String message) {
        super(message);
    }

    public RequestDeniedChangingStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestDeniedChangingStatusException(Throwable cause) {
        super(cause);
    }

    public RequestDeniedChangingStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
