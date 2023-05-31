package com.senla.common.exception.aspects;

public class MethodLoggerAspectException extends CommonAspectException {

    public MethodLoggerAspectException() {
    }

    public MethodLoggerAspectException(String message) {
        super(message);
    }

    public MethodLoggerAspectException(String message, Throwable cause) {
        super(message, cause);
    }

    public MethodLoggerAspectException(Throwable cause) {
        super(cause);
    }

    public MethodLoggerAspectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
