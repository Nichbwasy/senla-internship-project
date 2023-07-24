package com.senla.car.client;

import com.senla.car.dto.CarDto;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;

import java.util.List;

public interface CarMicroserviceClient {

    /**
     * Returns car with specified id if it exists
     * @param id Car id
     * @return Car dto with specified id
     */
    CarDto getCarById(Long id);

    /**
     * Returns page of filtered cars
     * @param page Page number
     * @param catalogFilterForm Fileter form dto
     * @return List of filtered cars
     */
    List<CarDto> getFilteredCars(Integer page, CarsCatalogFilterForm catalogFilterForm);

}
