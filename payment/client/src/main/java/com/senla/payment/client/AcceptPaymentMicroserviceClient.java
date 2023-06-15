package com.senla.payment.client;

import com.senla.payment.dto.clients.AcceptPaymentDto;

public interface AcceptPaymentMicroserviceClient {

    CarRentalReceiptDto acceptPayment(AcceptPaymentDto acceptPaymentDto);

}
