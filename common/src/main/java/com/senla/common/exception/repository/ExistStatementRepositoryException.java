package com.senla.common.exception.repository;

public class ExistStatementRepositoryException extends CommonRepositoryException {
    public ExistStatementRepositoryException() {
    }

    public ExistStatementRepositoryException(String message) {
        super(message);
    }

    public ExistStatementRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExistStatementRepositoryException(Throwable cause) {
        super(cause);
    }

    public ExistStatementRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
