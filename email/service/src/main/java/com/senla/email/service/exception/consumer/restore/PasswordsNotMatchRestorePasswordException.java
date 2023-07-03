package com.senla.email.service.exception.consumer.restore;

public class PasswordsNotMatchRestorePasswordException extends RestorePasswordServiceException {
    public PasswordsNotMatchRestorePasswordException() {
    }

    public PasswordsNotMatchRestorePasswordException(String message) {
        super(message);
    }

    public PasswordsNotMatchRestorePasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordsNotMatchRestorePasswordException(Throwable cause) {
        super(cause);
    }

    public PasswordsNotMatchRestorePasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
