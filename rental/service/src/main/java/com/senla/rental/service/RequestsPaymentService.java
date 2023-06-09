package com.senla.rental.service;

import com.senla.payment.dto.CarRentalReceiptDto;

public interface RequestsPaymentService {

    CarRentalReceiptDto payRequest(Long userId, Long requestId);

}
