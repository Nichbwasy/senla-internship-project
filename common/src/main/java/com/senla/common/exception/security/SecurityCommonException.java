package com.senla.common.exception.security;

public class SecurityCommonException extends RuntimeException {
    public SecurityCommonException() {
    }

    public SecurityCommonException(String message) {
        super(message);
    }

    public SecurityCommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityCommonException(Throwable cause) {
        super(cause);
    }

    public SecurityCommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
