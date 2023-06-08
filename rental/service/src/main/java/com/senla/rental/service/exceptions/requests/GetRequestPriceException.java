package com.senla.rental.service.exceptions.requests;

public class GetRequestPriceException extends RequestServiceException {
    public GetRequestPriceException() {
    }

    public GetRequestPriceException(String message) {
        super(message);
    }

    public GetRequestPriceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetRequestPriceException(Throwable cause) {
        super(cause);
    }

    public GetRequestPriceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
