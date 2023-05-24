package com.senla.rental.service.exceptions.profiles;

public class RefundRecordNotBelongUserProfileException extends UserProfileServiceException {
    public RefundRecordNotBelongUserProfileException() {
    }

    public RefundRecordNotBelongUserProfileException(String message) {
        super(message);
    }

    public RefundRecordNotBelongUserProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefundRecordNotBelongUserProfileException(Throwable cause) {
        super(cause);
    }

    public RefundRecordNotBelongUserProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
