package com.senla.car.service.impl;

import com.senla.car.dao.CarsRepository;
import com.senla.car.dto.CarDto;
import com.senla.car.dto.controllers.CarsCatalogFilterForm;
import com.senla.car.model.Car;
import com.senla.car.service.CarService;
import com.senla.car.service.mappers.CarMapper;
import com.senla.common.constants.pagination.PaginationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CarServiceImpl implements CarService {

    @Value("${cars.catalog.page.size:4}")
    private Integer PAGE_SIZE;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private CarMapper carMapper;

    @Override
    @Transactional
    public CarDto insert(CarDto carDto) {
        Car car = carMapper.mapToModel(carDto);
        car = carsRepository.insert(car);
        log.info("A new car has been inserted into the database. {}", car);
        return carMapper.mapToDto(car);
    }

    @Override
    @Transactional
    public CarDto update(CarDto carDto) {
        Car car = carMapper.mapToModel(carDto);
        car = carsRepository.update(car);
        log.info("The car with id '{}' has been updated in database. {}", car.getId(), car);
        return carMapper.mapToDto(car);
    }

    @Override
    public CarDto select(Long id) {
        Car car = carsRepository.select(id);
        log.info("The car with id '{}' has been selected from database.", id);
        return carMapper.mapToDto(car);
    }

    @Override
    public List<CarDto> selectAll() {
        List<Car> cars = carsRepository.selectAll();
        log.info("All cars has been selected from database.");
        return cars.stream().map(c -> carMapper.mapToDto(c)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Long delete(Long id) {
        Long deletedId = carsRepository.delete(id);
        log.info("Car with id '{}' has been removed from database.", deletedId);
        return deletedId;
    }


    @Override
    public List<CarDto> selectAllByStatus(String status) {
        List<Car> cars = carsRepository.selectAllByStatusName(status);
        log.info("All {} cars with status '{}' has been found in database.", cars.size(), status);
        return cars.stream().map(c -> carMapper.mapToDto(c)).collect(Collectors.toList());
    }

    @Override
    public List<CarDto> selectAllWithFilter(Integer page, CarsCatalogFilterForm filterForm) {
        PaginationDto paginationDto = new PaginationDto(page, PAGE_SIZE);
        List<Car> cars = carsRepository.selectByFilter(paginationDto, filterForm);
        log.info("All {} cars was found in database by filter '{}'.", cars.size(), filterForm);
        return cars.stream().map(c -> carMapper.mapToDto(c)).collect(Collectors.toList());
    }
}
