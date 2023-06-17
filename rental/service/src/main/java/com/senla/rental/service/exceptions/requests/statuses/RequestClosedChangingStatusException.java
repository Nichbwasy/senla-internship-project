package com.senla.rental.service.exceptions.requests.statuses;

public class RequestClosedChangingStatusException extends RequestStatusChangingException {
    public RequestClosedChangingStatusException() {
    }

    public RequestClosedChangingStatusException(String message) {
        super(message);
    }

    public RequestClosedChangingStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestClosedChangingStatusException(Throwable cause) {
        super(cause);
    }

    public RequestClosedChangingStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
