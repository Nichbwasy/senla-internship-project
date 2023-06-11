package com.senla.rental.service.exceptions.catalog;

import com.senla.common.exception.service.CommonServiceException;

public class CatalogServiceException extends CommonServiceException {

    public CatalogServiceException() {
    }

    public CatalogServiceException(String message) {
        super(message);
    }

    public CatalogServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CatalogServiceException(Throwable cause) {
        super(cause);
    }

    public CatalogServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
