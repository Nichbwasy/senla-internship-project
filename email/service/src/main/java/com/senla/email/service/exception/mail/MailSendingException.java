package com.senla.email.service.exception.mail;

import com.senla.common.exception.service.CommonServiceException;

public class MailSendingException extends CommonServiceException {
    public MailSendingException() {
    }

    public MailSendingException(String message) {
        super(message);
    }

    public MailSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MailSendingException(Throwable cause) {
        super(cause);
    }

    public MailSendingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
