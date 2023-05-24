package com.senla.common.exception.client;

public class MicroserviceResponseException extends MicroserviceClientException {
    public MicroserviceResponseException() {
    }

    public MicroserviceResponseException(String message) {
        super(message);
    }

    public MicroserviceResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public MicroserviceResponseException(Throwable cause) {
        super(cause);
    }

    public MicroserviceResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
