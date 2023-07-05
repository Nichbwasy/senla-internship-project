package com.senla.authorization.service.exceptions.services.email;

import com.senla.common.exception.service.CommonServiceException;

public class EmailServiceException extends CommonServiceException {

    public EmailServiceException() {
    }

    public EmailServiceException(String message) {
        super(message);
    }

    public EmailServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailServiceException(Throwable cause) {
        super(cause);
    }

    public EmailServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
