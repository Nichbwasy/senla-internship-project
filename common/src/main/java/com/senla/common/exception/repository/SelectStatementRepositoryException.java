package com.senla.common.exception.repository;

public class SelectStatementRepositoryException extends CommonRepositoryException {
    public SelectStatementRepositoryException() {
    }

    public SelectStatementRepositoryException(String message) {
        super(message);
    }

    public SelectStatementRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public SelectStatementRepositoryException(Throwable cause) {
        super(cause);
    }

    public SelectStatementRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
