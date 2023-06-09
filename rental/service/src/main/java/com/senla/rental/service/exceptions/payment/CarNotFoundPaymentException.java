package com.senla.rental.service.exceptions.payment;

public class CarNotFoundPaymentException extends PaymentRentalServiceException {
    public CarNotFoundPaymentException() {
    }

    public CarNotFoundPaymentException(String message) {
        super(message);
    }

    public CarNotFoundPaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarNotFoundPaymentException(Throwable cause) {
        super(cause);
    }

    public CarNotFoundPaymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
