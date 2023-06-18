package com.senla.payment.service;

import com.senla.payment.dto.PaymentReceiptDto;
import com.senla.payment.dto.PaymentRequestDto;

import java.util.List;

public interface PaymentRequestService {

    List<PaymentRequestDto> getPaymentRequestPage(Integer page);

    Boolean existRequestWithOrderNumber(String orderNumber);

    PaymentRequestDto getPayment(String id);

    PaymentRequestDto createPaymentRequest(PaymentRequestDto paymentRequestDto);

    PaymentReceiptDto acceptPaymentRequest(String requestId);

}
