package com.senla.car.client.impl;

import com.senla.car.client.CarMicroserviceClient;
import com.senla.car.dto.CarDto;
import com.senla.car.dto.client.CarsCatalogBodyFormDto;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import com.senla.common.clients.MicroserviceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CarMicroserviceClientImpl extends MicroserviceClient implements CarMicroserviceClient {

    public CarMicroserviceClientImpl(
            @Value("${microservice.name}") String microserviceName,
            @Value("${car.microservice.url}") String microserviceUrl
    ) {
        MICROSERVICE_NAME = microserviceName;
        MICROSERVICE_URL = microserviceUrl;
    }

    @Override
    public CarDto getCarById(Long id) {
        String path = "/cars/" + id;
        log.info("Sending a request to the '{}' from '{}' to get a car with id '{}'.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME, id);
        return this.sendRequest(path, HttpMethod.GET, CarDto.class, null, null);
    }

    @Override
    public List<CarDto> getFilteredCars(Integer page, CarsCatalogFilterForm catalogFilterForm) {
        String path = "/cars/filter";
        Class<List<CarDto>> clazz = (Class) List.class;
        CarsCatalogBodyFormDto bodyFormDto = new CarsCatalogBodyFormDto(page, catalogFilterForm);
        log.info("Sending a request to the '{}' from '{}' to get a filtered cars.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME);
        return this.sendRequest(path, HttpMethod.POST, clazz, null, bodyFormDto);
    }
}
