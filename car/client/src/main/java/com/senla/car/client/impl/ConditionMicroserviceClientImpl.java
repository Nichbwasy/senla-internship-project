package com.senla.car.client.impl;

import com.senla.car.client.ConditionMicroserviceClient;
import com.senla.common.clients.MicroserviceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConditionMicroserviceClientImpl extends MicroserviceClient implements ConditionMicroserviceClient {

    public ConditionMicroserviceClientImpl(
            @Value("${microservice.name}") String microserviceName,
            @Value("${car.microservice.url}") String microserviceUrl
    ) {
        MICROSERVICE_NAME = microserviceName;
        MICROSERVICE_URL = microserviceUrl;
    }

    @Override
    public Boolean existsByName(String name) {
        String path = "/conditions/existence?name=" + name;
        log.info("Sending a request to the '{}' from '{}' to check if condition '{}' exists.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME, name);
        return this.sendRequest(path, HttpMethod.GET, Boolean.class, null, null);
    }
}
