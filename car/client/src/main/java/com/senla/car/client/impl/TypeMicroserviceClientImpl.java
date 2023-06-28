package com.senla.car.client.impl;

import com.senla.car.client.TypeMicroserviceClient;
import com.senla.common.clients.MicroserviceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TypeMicroserviceClientImpl extends MicroserviceClient implements TypeMicroserviceClient {

    public TypeMicroserviceClientImpl(
            @Value("${microservice.name}") String microserviceName,
            @Value("${car.microservice.url}") String microserviceUrl
    ) {
        MICROSERVICE_NAME = microserviceName;
        MICROSERVICE_URL = microserviceUrl;
    }

    @Override
    public Boolean existByName(String name) {
        String path = "/types/existence/" + name;
        log.info("Sending a request to the '{}' from '{}' to check if condition '{}' exists.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME, name);
        return this.sendRequest(path, HttpMethod.GET, Boolean.class, null, null);
    }
}
