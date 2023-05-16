package com.senla.authorization.client.impl;

import com.senla.authorization.client.UserDataMicroserviceClient;
import com.senla.authorization.dto.UserDataDto;
import com.senla.common.clients.MicroserviceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDataMicroserviceClientImpl extends MicroserviceClient implements UserDataMicroserviceClient {

    public UserDataMicroserviceClientImpl(
            @Value("${microservice.name}") String microserviceName,
            @Value("${authorization.microservice.url}") String microserviceUrl
    ) {
        MICROSERVICE_NAME = microserviceName;
        MICROSERVICE_URL = microserviceUrl;
    }

    @Override
    public UserDataDto getUserDataByUserId(Long id) {
        String path = "/users/" + id;
        log.info("Sending a request to the '{}' from '{}' to get a user data with id '{}'.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME, id);
        return this.sendRequest(path, HttpMethod.GET, UserDataDto.class, null, null);
    }
}
