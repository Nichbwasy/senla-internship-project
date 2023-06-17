package com.senla.rental.service.exceptions.payment.statuses;

public class RequestAlreadyPaidPaymentException extends PaymentRequestStatusException {
    public RequestAlreadyPaidPaymentException() {
    }

    public RequestAlreadyPaidPaymentException(String message) {
        super(message);
    }

    public RequestAlreadyPaidPaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestAlreadyPaidPaymentException(Throwable cause) {
        super(cause);
    }

    public RequestAlreadyPaidPaymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
