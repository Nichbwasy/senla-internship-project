package com.senla.common.exception.client;

public class MicroserviceClientException extends RuntimeException {
    public MicroserviceClientException() {
    }

    public MicroserviceClientException(String message) {
        super(message);
    }

    public MicroserviceClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public MicroserviceClientException(Throwable cause) {
        super(cause);
    }

    public MicroserviceClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
