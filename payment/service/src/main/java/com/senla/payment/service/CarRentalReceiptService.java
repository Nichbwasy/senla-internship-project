package com.senla.payment.service;

import com.senla.payment.dto.CarRentalReceiptDto;
import com.senla.payment.dto.clients.AcceptPaymentDto;
import com.senla.payment.model.CarRentalReceipt;

import java.util.List;

public interface CarRentalReceiptService {

    CarRentalReceiptDto getCarRentalReceipt(Long id);
    List<CarRentalReceiptDto> getAllCarRentalReceipt();
    List<CarRentalReceiptDto> getUserCarRentalReceiptsPage(Long userId, Integer page);
    CarRentalReceiptDto acceptPayment(AcceptPaymentDto acceptPaymentDto);


}
