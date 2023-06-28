package com.senla.rental.service.exceptions.payment.statuses;

public class RequestDeniedPaymentException extends PaymentRequestStatusException {
    public RequestDeniedPaymentException() {
    }

    public RequestDeniedPaymentException(String message) {
        super(message);
    }

    public RequestDeniedPaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestDeniedPaymentException(Throwable cause) {
        super(cause);
    }

    public RequestDeniedPaymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
