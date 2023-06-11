package com.senla.payment.service.exceptions.receipts;

public class RequestStatusNotFoundReceiptException extends ReceiptsServiceException{
    public RequestStatusNotFoundReceiptException() {
    }

    public RequestStatusNotFoundReceiptException(String message) {
        super(message);
    }

    public RequestStatusNotFoundReceiptException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestStatusNotFoundReceiptException(Throwable cause) {
        super(cause);
    }

    public RequestStatusNotFoundReceiptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
