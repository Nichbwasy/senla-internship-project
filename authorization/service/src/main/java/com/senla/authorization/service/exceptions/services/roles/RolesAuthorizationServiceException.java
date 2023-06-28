package com.senla.authorization.service.exceptions.services.roles;

import com.senla.common.exception.service.CommonServiceException;

public class RolesAuthorizationServiceException extends CommonServiceException {

    public RolesAuthorizationServiceException() {
    }

    public RolesAuthorizationServiceException(String message) {
        super(message);
    }

    public RolesAuthorizationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RolesAuthorizationServiceException(Throwable cause) {
        super(cause);
    }

    public RolesAuthorizationServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
