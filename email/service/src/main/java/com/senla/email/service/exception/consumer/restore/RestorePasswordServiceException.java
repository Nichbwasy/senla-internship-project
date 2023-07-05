package com.senla.email.service.exception.consumer.restore;

import com.senla.common.exception.service.CommonServiceException;

public class RestorePasswordServiceException extends CommonServiceException {
    public RestorePasswordServiceException() {
    }

    public RestorePasswordServiceException(String message) {
        super(message);
    }

    public RestorePasswordServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestorePasswordServiceException(Throwable cause) {
        super(cause);
    }

    public RestorePasswordServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
