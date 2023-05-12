package com.senla.authorization.service.exceptions.services.registration;

public class LoginInUseException extends RegistrationServiceException {
    public LoginInUseException() {
    }

    public LoginInUseException(String message) {
        super(message);
    }

    public LoginInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginInUseException(Throwable cause) {
        super(cause);
    }

    public LoginInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
