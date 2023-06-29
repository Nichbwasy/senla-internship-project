package com.senla.email.service.exception.consumer;

import com.senla.common.exception.service.CommonServiceException;

public class RabbitConsumerException extends CommonServiceException {
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
