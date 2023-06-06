package com.senla.common.exception.service;

public class CommonServiceException extends RuntimeException {
    public CommonServiceException() {
    }

    public CommonServiceException(String message) {
        super(message);
    }

    public CommonServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonServiceException(Throwable cause) {
        super(cause);
    }

    public CommonServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
