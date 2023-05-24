package com.senla.authorization.service.exceptions.services.users;

import com.senla.authorization.service.exceptions.services.AuthorizationServicesException;

public class UserControlServiceException extends AuthorizationServicesException {
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
