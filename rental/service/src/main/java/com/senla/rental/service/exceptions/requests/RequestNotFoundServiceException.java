package com.senla.rental.service.exceptions.requests;

public class RequestNotFoundServiceException extends RequestServiceException{
    public RequestNotFoundServiceException() {
    }

    public RequestNotFoundServiceException(String message) {
        super(message);
    }

    public RequestNotFoundServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestNotFoundServiceException(Throwable cause) {
        super(cause);
    }

    public RequestNotFoundServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
