package com.senla.common.exception.repository;

public class UpdateStatementRepositoryException extends CommonRepositoryException {
    public UpdateStatementRepositoryException() {
    }

    public UpdateStatementRepositoryException(String message) {
        super(message);
    }

    public UpdateStatementRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateStatementRepositoryException(Throwable cause) {
        super(cause);
    }

    public UpdateStatementRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
