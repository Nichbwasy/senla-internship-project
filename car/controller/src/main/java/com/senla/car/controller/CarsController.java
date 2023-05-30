package com.senla.car.controller;

import com.senla.car.dto.CarDto;
import com.senla.car.dto.client.CarsCatalogBodyFormDto;
import com.senla.car.service.CarService;
import com.senla.common.annotations.LogMethodExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/cars")
public class CarsController {

    @Autowired
    private CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long id) {
        log.info("Trying to get the car with id '{}'...", id);
        return ResponseEntity.ok().body(carService.select(id));
    }

    @PostMapping
    public ResponseEntity<CarDto> addCar(@ModelAttribute CarDto carDto) {
        log.info("Trying to add a new car '{}'...", carDto);
        return ResponseEntity.ok().body(carService.insert(carDto));
    }

    @PutMapping
    public ResponseEntity<CarDto> updateCar(@RequestBody CarDto carDto) {
        log.info("Trying to update the car with id '{}'...", carDto.getId());
        return ResponseEntity.ok().body(carService.update(carDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCar(@PathVariable Long id) {
        log.info("Trying to delete the car with id '{}'...", id);
        return ResponseEntity.ok().body(carService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCars() {
        log.info("Trying to get all cars from repository...");
        return ResponseEntity.ok().body(carService.selectAll());
    }

    @PostMapping("/filter")
    public ResponseEntity<List<CarDto>> getAllCars(@RequestBody CarsCatalogBodyFormDto bodyFormDto) {
        log.info("Trying to get all filtered cars...");
        return ResponseEntity.ok().body(
                carService.selectAllWithFilter(bodyFormDto.getPage(), bodyFormDto.getCarsCatalogFilterForm())
        );
    }


}
