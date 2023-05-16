package com.senla.rental.service.exceptions.requests;

public class RequestAlreadyCanceledRequestException extends RequestServiceException {
    public RequestAlreadyCanceledRequestException() {
    }

    public RequestAlreadyCanceledRequestException(String message) {
        super(message);
    }

    public RequestAlreadyCanceledRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestAlreadyCanceledRequestException(Throwable cause) {
        super(cause);
    }

    public RequestAlreadyCanceledRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
