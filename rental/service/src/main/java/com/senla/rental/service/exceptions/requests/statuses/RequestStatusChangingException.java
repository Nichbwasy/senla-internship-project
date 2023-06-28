package com.senla.rental.service.exceptions.requests.statuses;

import com.senla.rental.service.exceptions.requests.RequestServiceException;

public class RequestStatusChangingException extends RequestServiceException {
    public RequestStatusChangingException() {
    }

    public RequestStatusChangingException(String message) {
        super(message);
    }

    public RequestStatusChangingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestStatusChangingException(Throwable cause) {
        super(cause);
    }

    public RequestStatusChangingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
