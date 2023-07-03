package com.senla.authorization.service.exceptions.services.restore;

import com.senla.common.exception.service.CommonServiceException;

public class PasswordRestoreServiceException extends CommonServiceException {
    public PasswordRestoreServiceException() {
    }

    public PasswordRestoreServiceException(String message) {
        super(message);
    }

    public PasswordRestoreServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordRestoreServiceException(Throwable cause) {
        super(cause);
    }

    public PasswordRestoreServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
