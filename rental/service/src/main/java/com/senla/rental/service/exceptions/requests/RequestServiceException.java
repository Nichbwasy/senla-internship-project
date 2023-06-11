package com.senla.rental.service.exceptions.requests;

import com.senla.common.exception.service.CommonServiceException;

public class RequestServiceException extends CommonServiceException {
    public RequestServiceException() {
    }

    public RequestServiceException(String message) {
        super(message);
    }

    public RequestServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestServiceException(Throwable cause) {
        super(cause);
    }

    public RequestServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
