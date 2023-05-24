package com.senla.authorization.service.exceptions.encoders;

public class PasswordEncoderException extends RuntimeException {
    public PasswordEncoderException() {
    }

    public PasswordEncoderException(String message) {
        super(message);
    }

    public PasswordEncoderException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordEncoderException(Throwable cause) {
        super(cause);
    }

    public PasswordEncoderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
