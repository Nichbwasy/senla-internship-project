package com.senla.email.service.exception.service.confirm;

public class MailingRequestNotFoundException extends EmailConfirmNotificationServiceException {
    public MailingRequestNotFoundException() {
    }

    public MailingRequestNotFoundException(String message) {
        super(message);
    }

    public MailingRequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailingRequestNotFoundException(Throwable cause) {
        super(cause);
    }

    public MailingRequestNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
