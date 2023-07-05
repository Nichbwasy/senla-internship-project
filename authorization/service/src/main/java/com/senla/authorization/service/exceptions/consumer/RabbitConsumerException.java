package com.senla.authorization.service.exceptions.consumer;

public class RabbitConsumerException extends RuntimeException {
    public RabbitConsumerException() {
    }

    public RabbitConsumerException(String message) {
        super(message);
    }

    public RabbitConsumerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RabbitConsumerException(Throwable cause) {
        super(cause);
    }

    public RabbitConsumerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
