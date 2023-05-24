package com.senla.car.dao;

import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import com.senla.car.model.Car;
import com.senla.common.constants.pagination.PaginationDto;
import com.senla.common.dao.CrudRepository;
import jakarta.validation.Valid;

import java.util.List;

public interface CarsRepository extends CrudRepository<Car, Long> {
    List<Car> selectAllByMileageDiapason(Double min, Double max);
    List<Car> selectAllByStatusName(String statusName);
    List<Car> selectAllByConditionNameCriteria(String conditionName);

    List<Car> selectAllByConditionNameJPQL(String conditionName);
    Car selectByIdEntityGraphWithFetches(Long id);
    Car selectByIdEntityGraphWithoutFetches(Long id);
    List<Car> selectByFilter(@Valid PaginationDto paginationDto, CarsCatalogFilterForm filterForm);
}
