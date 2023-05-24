package com.senla.rental.service.exceptions.refunds;

public class CarRequestNotFoundRefundException extends CarRefundServiceException{
    public CarRequestNotFoundRefundException() {
    }

    public CarRequestNotFoundRefundException(String message) {
        super(message);
    }

    public CarRequestNotFoundRefundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarRequestNotFoundRefundException(Throwable cause) {
        super(cause);
    }

    public CarRequestNotFoundRefundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
