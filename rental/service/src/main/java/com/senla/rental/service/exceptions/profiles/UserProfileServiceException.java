package com.senla.rental.service.exceptions.profiles;

import com.senla.common.exception.service.CommonServiceException;

public class UserProfileServiceException extends CommonServiceException {
    public UserProfileServiceException() {
    }

    public UserProfileServiceException(String message) {
        super(message);
    }

    public UserProfileServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserProfileServiceException(Throwable cause) {
        super(cause);
    }

    public UserProfileServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
