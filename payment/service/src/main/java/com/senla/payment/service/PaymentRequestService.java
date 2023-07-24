package com.senla.payment.service;

import com.senla.payment.dto.PaymentReceiptDto;
import com.senla.payment.dto.PaymentRequestDto;

import java.util.List;

public interface PaymentRequestService {

    /**
     * Shows page of payment requests
     * @param page Page number
     * @return List of payment requests for the specified page
     */
    List<PaymentRequestDto> getPaymentRequestPage(Integer page);

    /**
     * Checks if order with specified number exists
     * @param orderNumber Order number
     * @return True if order with specified found, otherwise false
     */
    Boolean existRequestWithOrderNumber(String orderNumber);

    /**
     * Gets payment request by id
     * @param id Id of payment request
     * @return Payment request with specified id
     */
    PaymentRequestDto getPayment(String id);

    /**
     * Creates a new payment request record
     * @param paymentRequestDto Payment request form
     * @return Created payment request record
     */
    PaymentRequestDto createPaymentRequest(PaymentRequestDto paymentRequestDto);

    /**
     * Accepts payment for request with specified id and send notification
     * to the kafka query specified in the payment request
     * @param requestId Id of payment request
     * @return Updated payment request record
     */
    PaymentReceiptDto acceptPaymentRequest(String requestId);

}
