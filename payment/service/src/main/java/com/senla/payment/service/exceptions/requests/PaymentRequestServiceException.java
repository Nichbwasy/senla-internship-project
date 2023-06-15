package com.senla.payment.service.exceptions.requests;

public class PaymentRequestServiceException extends RuntimeException {
    public PaymentRequestServiceException() {
    }

    public PaymentRequestServiceException(String message) {
        super(message);
    }

    public PaymentRequestServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentRequestServiceException(Throwable cause) {
        super(cause);
    }

    public PaymentRequestServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
