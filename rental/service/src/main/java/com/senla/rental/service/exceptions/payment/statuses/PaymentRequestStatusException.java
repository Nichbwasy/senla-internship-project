package com.senla.rental.service.exceptions.payment.statuses;

import com.senla.rental.service.exceptions.payment.PaymentRentalServiceException;

public class PaymentRequestStatusException extends PaymentRentalServiceException {
    public PaymentRequestStatusException() {
    }

    public PaymentRequestStatusException(String message) {
        super(message);
    }

    public PaymentRequestStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaymentRequestStatusException(Throwable cause) {
        super(cause);
    }

    public PaymentRequestStatusException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
