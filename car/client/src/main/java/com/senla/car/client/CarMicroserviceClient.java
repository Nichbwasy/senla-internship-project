package com.senla.car.client;

import com.senla.car.dto.CarDto;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;

import java.util.List;

public interface CarMicroserviceClient {

    CarDto getCarById(Long id);
    List<CarDto> getFilteredCars(Integer page, CarsCatalogFilterForm catalogFilterForm);

}
