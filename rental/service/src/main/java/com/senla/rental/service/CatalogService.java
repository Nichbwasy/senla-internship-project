package com.senla.rental.service;


import com.senla.car.dto.CarDto;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.controller.CarOrderingRequestDto;

import java.util.List;

public interface CatalogService {
    /**
     * Show all filtered cars
     * @param page Page number
     * @param catalogFilterForm Filtering form
     * @return List of found filtered cars
     */
    List<CarDto> showAllFilteredCars(Integer page, CarsCatalogFilterForm catalogFilterForm);

    /**
     * Creates car order for the user
     * @param accessToken User's access token
     * @param carOrderingRequestDto Ordering form
     * @return Created request
     */
    RequestDto createCarOrderingRequest(String accessToken, CarOrderingRequestDto carOrderingRequestDto);

}
