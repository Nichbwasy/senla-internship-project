package com.senla.authorization.service.exceptions.services.users;

import com.senla.common.exception.service.CommonServiceException;

public class UserControlServiceException extends CommonServiceException {
    public UserControlServiceException() {
    }

    public UserControlServiceException(String message) {
        super(message);
    }

    public UserControlServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserControlServiceException(Throwable cause) {
        super(cause);
    }

    public UserControlServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
