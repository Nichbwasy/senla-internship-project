package com.senla.email.service.exception.consumer.restore;

public class RequestNotFoundRestorePasswordException extends RestorePasswordServiceException {
    public RequestNotFoundRestorePasswordException() {
    }

    public RequestNotFoundRestorePasswordException(String message) {
        super(message);
    }

    public RequestNotFoundRestorePasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestNotFoundRestorePasswordException(Throwable cause) {
        super(cause);
    }

    public RequestNotFoundRestorePasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
