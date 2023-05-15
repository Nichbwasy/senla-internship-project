package com.senla.car.service.mappers;

import com.senla.car.dto.CarDto;
import com.senla.car.model.Car;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDto mapToDto(Car car);
    Car mapToModel(CarDto carDto);

}
