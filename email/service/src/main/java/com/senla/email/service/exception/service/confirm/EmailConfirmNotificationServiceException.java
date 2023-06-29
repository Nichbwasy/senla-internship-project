package com.senla.email.service.exception.service.confirm;

import com.senla.common.exception.service.CommonServiceException;

public class EmailConfirmNotificationServiceException extends CommonServiceException {
    public EmailConfirmNotificationServiceException() {
    }

    public EmailConfirmNotificationServiceException(String message) {
        super(message);
    }

    public EmailConfirmNotificationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailConfirmNotificationServiceException(Throwable cause) {
        super(cause);
    }

    public EmailConfirmNotificationServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
