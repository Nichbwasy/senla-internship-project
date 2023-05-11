package com.senla.common.exception.repository;

public class InsertStatementRepositoryException extends CommonRepositoryException{
    public InsertStatementRepositoryException() {
    }

    public InsertStatementRepositoryException(String message) {
        super(message);
    }

    public InsertStatementRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertStatementRepositoryException(Throwable cause) {
        super(cause);
    }

    public InsertStatementRepositoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
