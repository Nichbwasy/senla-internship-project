package com.senla.rental.service.exceptions.catalog;

public class CarAlreadyOrderedCatalogException extends CatalogServiceException {
    public CarAlreadyOrderedCatalogException() {
    }

    public CarAlreadyOrderedCatalogException(String message) {
        super(message);
    }

    public CarAlreadyOrderedCatalogException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarAlreadyOrderedCatalogException(Throwable cause) {
        super(cause);
    }

    public CarAlreadyOrderedCatalogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
