package com.senla.rental.service.exceptions.refunds;

public class UserNotFoundRefundException extends CarRefundServiceException {
    public UserNotFoundRefundException() {
    }

    public UserNotFoundRefundException(String message) {
        super(message);
    }

    public UserNotFoundRefundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundRefundException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundRefundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
