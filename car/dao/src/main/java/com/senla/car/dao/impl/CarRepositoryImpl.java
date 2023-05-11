package com.senla.car.dao.impl;

import com.senla.car.dao.CarRepository;
import com.senla.car.model.Car;
import com.senla.common.dao.CommonCrudRepository;

public class CarRepositoryImpl extends CommonCrudRepository<Car, Long> implements CarRepository {
}
