package com.senla.rental.client.impl;

import com.senla.common.clients.MicroserviceClient;
import com.senla.rental.client.RequestMicroserviceClient;
import com.senla.rental.dto.RequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RequestMicroserviceClientImpl extends MicroserviceClient implements RequestMicroserviceClient {

    public RequestMicroserviceClientImpl(
            @Value("${microservice.name}") String microserviceName,
            @Value("${rental.microservice.url}") String microserviceUrl
    ) {
        MICROSERVICE_NAME = microserviceName;
        MICROSERVICE_URL = microserviceUrl;
    }

    @Override
    public List<RequestDto> getAllUserRequests(Long userId) {
        String path = "/requests/users/" + userId;
        Class<List<RequestDto>> clazz = (Class) List.class;
        log.info("Sending a request to the '{}' from '{}' to get a all user '{}' rental requests.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME, userId);
        return this.sendRequest(path, HttpMethod.GET, clazz, null, null);
    }

    @Override
    public RequestDto updateRequestStatus(Long requestId, Long requestStatusId) {
        String path = "/requests/updating?requestId=" + requestId + "&requestStatusId=" + requestStatusId;
        log.info("Sending a request to the '{}' from '{}' to update request '{}' rental requests.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME, requestId);
        return this.sendRequest(path, HttpMethod.POST, RequestDto.class, null, null);
    }
}
