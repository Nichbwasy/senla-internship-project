package com.senla.rental.service;

public interface RequestsPaymentService {

    /**
     * Sends to the payment system payment request
     * @param userId Id of user
     * @param requestId Id of car ordering request
     * @return Notification message
     */
    String payRequest(Long userId, Long requestId);

}
