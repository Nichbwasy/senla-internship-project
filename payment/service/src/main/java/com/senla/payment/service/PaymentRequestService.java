package com.senla.payment.service;

import com.senla.payment.dto.PaymentReceiptDto;
import com.senla.payment.dto.PaymentRequestDto;
import com.senla.payment.model.PaymentRequest;

import java.util.List;

public interface PaymentRequestService {

    List<PaymentRequestDto> getPaymentRequestPage(Integer page);

    PaymentRequestDto getPayment(String id);

    PaymentRequestDto createPaymentRequest(PaymentRequestDto paymentRequestDto);

    PaymentReceiptDto acceptPaymentRequest(String requestId);

}
