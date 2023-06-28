package com.senla.payment.service.exceptions.requests;

public class RequestNotFoundRequestServiceException extends PaymentRequestServiceException {

    public RequestNotFoundRequestServiceException() {
    }

    public RequestNotFoundRequestServiceException(String message) {
        super(message);
    }

    public RequestNotFoundRequestServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestNotFoundRequestServiceException(Throwable cause) {
        super(cause);
    }

    public RequestNotFoundRequestServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
