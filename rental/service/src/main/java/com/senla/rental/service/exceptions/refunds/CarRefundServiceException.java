package com.senla.rental.service.exceptions.refunds;

import com.senla.rental.service.exceptions.CommonServiceException;

public class CarRefundServiceException extends CommonServiceException {
    public CarRefundServiceException() {
    }

    public CarRefundServiceException(String message) {
        super(message);
    }

    public CarRefundServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarRefundServiceException(Throwable cause) {
        super(cause);
    }

    public CarRefundServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
