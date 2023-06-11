package com.senla.payment.service.exceptions.receipts;

import com.senla.common.exception.service.CommonServiceException;

public class ReceiptsServiceException extends CommonServiceException {
    public ReceiptsServiceException() {
    }

    public ReceiptsServiceException(String message) {
        super(message);
    }

    public ReceiptsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReceiptsServiceException(Throwable cause) {
        super(cause);
    }

    public ReceiptsServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
