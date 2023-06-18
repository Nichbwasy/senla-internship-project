package com.senla.rental.service.exceptions.payment.statuses;

public class RequestCanceledPaymentException extends PaymentRequestStatusException {
    public RequestCanceledPaymentException() {
    }

    public RequestCanceledPaymentException(String message) {
        super(message);
    }

    public RequestCanceledPaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestCanceledPaymentException(Throwable cause) {
        super(cause);
    }

    public RequestCanceledPaymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
