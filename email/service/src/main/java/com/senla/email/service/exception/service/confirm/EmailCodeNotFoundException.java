package com.senla.email.service.exception.service.confirm;

public class EmailCodeNotFoundException extends EmailConfirmNotificationServiceException{
    public EmailCodeNotFoundException() {
    }

    public EmailCodeNotFoundException(String message) {
        super(message);
    }

    public EmailCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailCodeNotFoundException(Throwable cause) {
        super(cause);
    }

    public EmailCodeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
