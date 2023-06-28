package com.senla.payment.service.exceptions.receipts;

public class RequestAccessingPaymentReceiptException extends ReceiptsServiceException {

    public RequestAccessingPaymentReceiptException() {
    }

    public RequestAccessingPaymentReceiptException(String message) {
        super(message);
    }

    public RequestAccessingPaymentReceiptException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestAccessingPaymentReceiptException(Throwable cause) {
        super(cause);
    }

    public RequestAccessingPaymentReceiptException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
