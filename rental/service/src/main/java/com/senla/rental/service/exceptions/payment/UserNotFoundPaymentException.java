package com.senla.rental.service.exceptions.payment;

public class UserNotFoundPaymentException extends PaymentRentalServiceException{
    public UserNotFoundPaymentException() {
    }

    public UserNotFoundPaymentException(String message) {
        super(message);
    }

    public UserNotFoundPaymentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundPaymentException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundPaymentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
