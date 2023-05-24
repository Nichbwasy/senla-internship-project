package com.senla.rental.service.exceptions.catalog;

public class CarUnavailableCatalogException extends CatalogServiceException {
    public CarUnavailableCatalogException() {
    }

    public CarUnavailableCatalogException(String message) {
        super(message);
    }

    public CarUnavailableCatalogException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarUnavailableCatalogException(Throwable cause) {
        super(cause);
    }

    public CarUnavailableCatalogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
