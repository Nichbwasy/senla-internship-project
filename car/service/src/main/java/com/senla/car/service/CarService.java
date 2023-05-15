package com.senla.car.service;

import com.senla.car.dto.CarDto;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;

import java.util.List;

public interface CarService {
    CarDto insert(CarDto carDto);
    CarDto update(CarDto carDto);
    CarDto select(Long id);
    Long delete(Long id);
    List<CarDto> selectAll();
    List<CarDto> selectAllByStatus(String status);
    List<CarDto> selectAllWithFilter(Integer page, CarsCatalogFilterForm filterForm);
}
