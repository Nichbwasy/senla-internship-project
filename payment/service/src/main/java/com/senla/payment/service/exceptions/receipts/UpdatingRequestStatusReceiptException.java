package com.senla.payment.service.exceptions.receipts;

public class UpdatingRequestStatusReceiptException extends ReceiptsServiceException {
    public UpdatingRequestStatusReceiptException() {
    }

    public UpdatingRequestStatusReceiptException(String message) {
        super(message);
    }

    public UpdatingRequestStatusReceiptException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdatingRequestStatusReceiptException(Throwable cause) {
        super(cause);
    }

    public UpdatingRequestStatusReceiptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
