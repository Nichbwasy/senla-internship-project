package com.senla.authorization.service.exceptions.services.registration;

import com.senla.common.exception.service.CommonServiceException;

public class RegistrationServiceException extends CommonServiceException {
    public RegistrationServiceException() {
    }

    public RegistrationServiceException(String message) {
        super(message);
    }

    public RegistrationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegistrationServiceException(Throwable cause) {
        super(cause);
    }

    public RegistrationServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
