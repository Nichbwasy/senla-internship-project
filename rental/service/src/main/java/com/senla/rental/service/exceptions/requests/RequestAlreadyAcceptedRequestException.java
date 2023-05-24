package com.senla.rental.service.exceptions.requests;

public class RequestAlreadyAcceptedRequestException extends RequestServiceException {
    public RequestAlreadyAcceptedRequestException() {
    }

    public RequestAlreadyAcceptedRequestException(String message) {
        super(message);
    }

    public RequestAlreadyAcceptedRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestAlreadyAcceptedRequestException(Throwable cause) {
        super(cause);
    }

    public RequestAlreadyAcceptedRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
