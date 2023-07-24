package com.senla.car.dao;

import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import com.senla.car.model.Car;
import com.senla.common.constants.pagination.PaginationDto;
import com.senla.common.dao.CrudRepository;
import jakarta.validation.Valid;

import java.util.List;

public interface CarsRepository extends CrudRepository<Car, Long> {
    /**
     * Selects all cars in mileage diapason
     * @param min Min mileage value
     * @param max Max mileage value
     * @return Found cars list
     */
    List<Car> selectAllByMileageDiapason(Double min, Double max);

    /**
     * Selects all cats with specified status name
     * @param statusName Car's status name
     * @return Found cars list
     */
    List<Car> selectAllByStatusName(String statusName);

    /**
     * Selects all car with specified condition name
     * @param conditionName Car's condition name
     * @return Found cars list
     */
    List<Car> selectAllByConditionNameCriteria(String conditionName);

    /**
     * Selects all car with specified condition name (using JPQL)
     * @param conditionName Car's condition name
     * @return Found cars list
     */
    List<Car> selectAllByConditionNameJPQL(String conditionName);

    /**
     * Select a car by id (using entity graph with fetches)
     * @param id Car's id
     * @return Selected car
     */
    Car selectByIdEntityGraphWithFetches(Long id);

    /**
     * Select a car by id (using entity graph without fetches)
     * @param id Car's id
     * @return Selected car
     */
    Car selectByIdEntityGraphWithoutFetches(Long id);

    /**
     * Returns page of cars filtered via filter
     * @param paginationDto Dto for paging
     * @param filterForm
     * @return
     */
    List<Car> selectByFilter(@Valid PaginationDto paginationDto, CarsCatalogFilterForm filterForm);
}
