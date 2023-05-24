package com.senla.common.exception.repository;

public class CommonRepositoryException extends RuntimeException {
    public CommonRepositoryException() {
    }

    public CommonRepositoryException(String message) {
        super(message);
    }

    public CommonRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonRepositoryException(Throwable cause) {
        super(cause);
    }

    public CommonRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
