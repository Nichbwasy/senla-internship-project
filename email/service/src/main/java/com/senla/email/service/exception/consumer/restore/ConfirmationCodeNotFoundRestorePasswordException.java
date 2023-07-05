package com.senla.email.service.exception.consumer.restore;

public class ConfirmationCodeNotFoundRestorePasswordException extends RestorePasswordServiceException {
    public ConfirmationCodeNotFoundRestorePasswordException() {
    }

    public ConfirmationCodeNotFoundRestorePasswordException(String message) {
        super(message);
    }

    public ConfirmationCodeNotFoundRestorePasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfirmationCodeNotFoundRestorePasswordException(Throwable cause) {
        super(cause);
    }

    public ConfirmationCodeNotFoundRestorePasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
