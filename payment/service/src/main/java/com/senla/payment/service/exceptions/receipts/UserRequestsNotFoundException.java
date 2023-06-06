package com.senla.payment.service.exceptions.receipts;

public class UserRequestsNotFoundException extends ReceiptsServiceException {
    public UserRequestsNotFoundException() {
    }

    public UserRequestsNotFoundException(String message) {
        super(message);
    }

    public UserRequestsNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserRequestsNotFoundException(Throwable cause) {
        super(cause);
    }

    public UserRequestsNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
