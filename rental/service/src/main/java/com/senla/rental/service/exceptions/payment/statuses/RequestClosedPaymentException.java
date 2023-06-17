package com.senla.rental.service.exceptions.payment.statuses;

public class RequestClosedPaymentException extends PaymentRequestStatusException {
    public RequestClosedPaymentException() {
    }

    public RequestClosedPaymentException(String message) {
        super(message);
    }

    public RequestClosedPaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestClosedPaymentException(Throwable cause) {
        super(cause);
    }

    public RequestClosedPaymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
