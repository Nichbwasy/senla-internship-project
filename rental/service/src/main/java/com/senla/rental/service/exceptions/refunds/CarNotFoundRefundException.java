package com.senla.rental.service.exceptions.refunds;

public class CarNotFoundRefundException extends CarRefundServiceException {
    public CarNotFoundRefundException() {
    }

    public CarNotFoundRefundException(String message) {
        super(message);
    }

    public CarNotFoundRefundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarNotFoundRefundException(Throwable cause) {
        super(cause);
    }

    public CarNotFoundRefundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
