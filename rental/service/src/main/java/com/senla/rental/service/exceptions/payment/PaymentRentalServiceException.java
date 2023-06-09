package com.senla.rental.service.exceptions.payment;

import com.senla.common.exception.service.CommonServiceException;

public class PaymentRentalServiceException extends CommonServiceException {

    public PaymentRentalServiceException() {
    }

    public PaymentRentalServiceException(String message) {
        super(message);
    }

    public PaymentRentalServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentRentalServiceException(Throwable cause) {
        super(cause);
    }

    public PaymentRentalServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
