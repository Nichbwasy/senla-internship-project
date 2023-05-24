package com.senla.rental.service;


import com.senla.car.dto.CarDto;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.controller.CarOrderingRequestDto;

import java.util.List;

public interface CatalogService {
    List<CarDto> showAllFilteredCars(Integer page, CarsCatalogFilterForm catalogFilterForm);
    RequestDto createCarOrderingRequest(String accessToken, CarOrderingRequestDto carOrderingRequestDto);

}
