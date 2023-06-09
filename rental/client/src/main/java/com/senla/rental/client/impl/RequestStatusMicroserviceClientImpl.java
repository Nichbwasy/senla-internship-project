package com.senla.rental.client.impl;

import com.senla.common.clients.MicroserviceClient;
import com.senla.rental.client.RequestStatusMicroserviceClient;
import com.senla.rental.dto.RequestStatusDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RequestStatusMicroserviceClientImpl extends MicroserviceClient implements RequestStatusMicroserviceClient {

    public RequestStatusMicroserviceClientImpl(
            @Value("${microservice.name}") String microserviceName,
            @Value("${rental.microservice.url}") String microserviceUrl
    ) {
        MICROSERVICE_NAME = microserviceName;
        MICROSERVICE_URL = microserviceUrl;
    }

    @Override
    public RequestStatusDto getRequestStatusByName(String name) {
        String path = "/requests/statuses/status?name=" + name;
        log.info("Sending a request to the '{}' from '{}' to get request status'{}'.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME, name);
        return this.sendRequest(path, HttpMethod.GET, RequestStatusDto.class, null, null);
    }
}
