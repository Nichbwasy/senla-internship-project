package com.senla.payment.service.exceptions.receipts;

public class UserNotFoundReceiptsServiceException extends ReceiptsServiceException {
    public UserNotFoundReceiptsServiceException() {
    }

    public UserNotFoundReceiptsServiceException(String message) {
        super(message);
    }

    public UserNotFoundReceiptsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotFoundReceiptsServiceException(Throwable cause) {
        super(cause);
    }

    public UserNotFoundReceiptsServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
