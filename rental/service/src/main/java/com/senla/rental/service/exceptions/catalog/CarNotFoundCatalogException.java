package com.senla.rental.service.exceptions.catalog;

public class CarNotFoundCatalogException extends CatalogServiceException {
    public CarNotFoundCatalogException() {
    }

    public CarNotFoundCatalogException(String message) {
        super(message);
    }

    public CarNotFoundCatalogException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarNotFoundCatalogException(Throwable cause) {
        super(cause);
    }

    public CarNotFoundCatalogException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
