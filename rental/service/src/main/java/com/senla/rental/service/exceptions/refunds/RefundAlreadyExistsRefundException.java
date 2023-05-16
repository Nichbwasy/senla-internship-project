package com.senla.rental.service.exceptions.refunds;

public class RefundAlreadyExistsRefundException extends CarRefundServiceException {
    public RefundAlreadyExistsRefundException() {
    }

    public RefundAlreadyExistsRefundException(String message) {
        super(message);
    }

    public RefundAlreadyExistsRefundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefundAlreadyExistsRefundException(Throwable cause) {
        super(cause);
    }

    public RefundAlreadyExistsRefundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
