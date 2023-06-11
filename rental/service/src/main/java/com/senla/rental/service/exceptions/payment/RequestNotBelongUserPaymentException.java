package com.senla.rental.service.exceptions.payment;

public class RequestNotBelongUserPaymentException extends PaymentRentalServiceException {
    public RequestNotBelongUserPaymentException() {
    }

    public RequestNotBelongUserPaymentException(String message) {
        super(message);
    }

    public RequestNotBelongUserPaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestNotBelongUserPaymentException(Throwable cause) {
        super(cause);
    }

    public RequestNotBelongUserPaymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
