package com.senla.car.service;

import com.senla.car.dto.CarDto;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;

import java.util.List;

public interface CarService {
    /**
     * Inserts a new car record
     * @param carDto Car dto
     * @return Inserted car record
     */
    CarDto insert(CarDto carDto);

    /**
     * Updates a car record with specified in dto id
     * @param carDto Car dto to update
     * @return Updated car record
     */
    CarDto update(CarDto carDto);

    /**
     * Selects car record by id
     * @param id Car id
     * @return Selected car with specified id
     */
    CarDto select(Long id);

    /**
     * Removes car record with specified id
     * @param id Car id
     * @return Id of deleted car record
     */
    Long delete(Long id);

    /**
     * Selects all cars records
     * @return List of all car records
     */
    List<CarDto> selectAll();

    /**
     * Returns all cars with the same status
     * @param status Car status name
     * @return List of cars with the same status name
     */
    List<CarDto> selectAllByStatus(String status);

    /**
     * Returns all filtered cars
     * @param page Page number
     * @param filterForm Filtered form dto
     * @return List of all filtered cars
     */
    List<CarDto> selectAllWithFilter(Integer page, CarsCatalogFilterForm filterForm);
}
