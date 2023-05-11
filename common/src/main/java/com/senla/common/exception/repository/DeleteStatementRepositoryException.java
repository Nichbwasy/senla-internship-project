package com.senla.common.exception.repository;

public class DeleteStatementRepositoryException extends CommonRepositoryException {
    public DeleteStatementRepositoryException() {
    }

    public DeleteStatementRepositoryException(String message) {
        super(message);
    }

    public DeleteStatementRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeleteStatementRepositoryException(Throwable cause) {
        super(cause);
    }

    public DeleteStatementRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
