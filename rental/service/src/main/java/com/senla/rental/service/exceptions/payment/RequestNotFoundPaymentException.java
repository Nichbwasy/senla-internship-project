package com.senla.rental.service.exceptions.payment;

public class RequestNotFoundPaymentException extends PaymentRentalServiceException {
    public RequestNotFoundPaymentException() {
    }

    public RequestNotFoundPaymentException(String message) {
        super(message);
    }

    public RequestNotFoundPaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestNotFoundPaymentException(Throwable cause) {
        super(cause);
    }

    public RequestNotFoundPaymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
