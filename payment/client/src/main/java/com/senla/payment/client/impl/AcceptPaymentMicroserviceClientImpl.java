package com.senla.payment.client.impl;

import com.senla.common.clients.MicroserviceClient;
import com.senla.payment.client.AcceptPaymentMicroserviceClient;
import com.senla.payment.dto.clients.AcceptPaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AcceptPaymentMicroserviceClientImpl extends MicroserviceClient implements AcceptPaymentMicroserviceClient{

    public AcceptPaymentMicroserviceClientImpl(
            @Value("${microservice.name}") String microserviceName,
            @Value("${payment.microservice.url}") String microserviceUrl
    ) {
        MICROSERVICE_NAME = microserviceName;
        MICROSERVICE_URL = microserviceUrl;
    }

    @Override
    public CarRentalReceiptDto acceptPayment(AcceptPaymentDto acceptPaymentDto) {
        String path = "/payment/acceptation";
        log.info("Sending a request to the '{}' from '{}' to accept payment fot the request '{}'.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME, acceptPaymentDto.getRequestDto().getId());
        return this.sendRequest(path, HttpMethod.POST, CarRentalReceiptDto.class, null, acceptPaymentDto);
    }
}
