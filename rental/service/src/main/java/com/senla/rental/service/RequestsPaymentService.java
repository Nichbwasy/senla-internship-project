package com.senla.rental.service;

import com.senla.payment.dto.CarRentalReceiptDto;

public interface RequestsPaymentService {

    String payRequest(Long userId, Long requestId);

}
