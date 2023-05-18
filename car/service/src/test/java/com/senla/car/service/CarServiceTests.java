package com.senla.car.service;

import com.senla.car.dao.CarsRepository;
import com.senla.car.dto.CarDto;
import com.senla.car.model.Car;
import com.senla.car.service.impl.CarServiceImpl;
import com.senla.car.service.mappers.CarMapper;
import com.senla.common.exception.repository.DeleteStatementRepositoryException;
import com.senla.common.exception.repository.EntityNotFoundException;
import com.senla.common.exception.repository.InsertStatementRepositoryException;
import com.senla.common.exception.repository.UpdateStatementRepositoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CarServiceTests {

    @Spy
    private final CarMapper carsMapper = Mappers.getMapper(CarMapper.class);

    @Mock
    private CarsRepository carsRepository;

    @InjectMocks
    private final CarServiceImpl carService = new CarServiceImpl();

    @Test
    public void insertTest() {
        CarDto carDto = new CarDto(1L, "Description", 50000D, null, null, null);
        Mockito.lenient().when(carsRepository.insert(Mockito.any(Car.class))).thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(carDto, carService.insert(carDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient().when(carsRepository.insert(Mockito.nullable(Car.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> carService.insert(null));
    }

    @Test
    public void updateTest() {
        CarDto carDto = new CarDto(1L, "Description", 50000D, null, null, null);
        Mockito.lenient()
                .when(carsRepository.update(Mockito.any(Car.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(carDto, carService.update(carDto));
    }

    @Test
    public void updateNotExistedTest() {
        CarDto carDto = new CarDto(2L, "Description", 50000D, null, null, null);
        Mockito.when(carsRepository.update(Mockito.any(Car.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> carService.update(carDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.lenient()
                .when(carsRepository.update(Mockito.nullable(Car.class)))
                .thenThrow(UpdateStatementRepositoryException.class);

        Assertions.assertThrows(UpdateStatementRepositoryException.class, () -> carService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient()
                .when(carsRepository.delete(1L))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(1, carService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .when(carsRepository.delete(Mockito.anyLong()))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> carService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .when(carsRepository.delete(Mockito.nullable(Long.class)))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> carService.delete(null));
    }

    @Test
    public void selectTest() {
        Car car = new Car(1L, "Description", 50000D, null, null, null);
        Mockito.when(carsRepository.select(1L)).thenReturn(car);

        Assertions.assertEquals(carsMapper.mapToDto(car), carService.select(1L));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(carsRepository.select(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> carService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(carsRepository.select(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> carService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car(5L, "Desc1", 50000D, null, null, null));
        cars.add(new Car(6L, "Desc2", 80000D, null, null, null));

        Mockito.lenient().when(carsRepository.selectAll()).thenReturn(cars);

        List<CarDto> carDtos = carService.selectAll();
        Assertions.assertEquals(2, carDtos.size());
        for (int i = 0; i < carDtos.size(); i++) {
            Assertions.assertEquals(carDtos.get(i), carsMapper.mapToDto(cars.get(i)));
        }
    }
}
