package com.senla.common.exception.aspects;

public class CommonAspectException extends RuntimeException {
    public CommonAspectException() {
    }

    public CommonAspectException(String message) {
        super(message);
    }

    public CommonAspectException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonAspectException(Throwable cause) {
        super(cause);
    }

    public CommonAspectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
