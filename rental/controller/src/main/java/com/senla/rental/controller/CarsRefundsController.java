package com.senla.rental.controller;

import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.service.CarsRefundsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/car/refunds")
public class CarsRefundsController {

    @Autowired
    private CarsRefundsService carsRefundsService;

    @GetMapping("/{id}")
    public ResponseEntity<CarRefundDto> getCarRefund(@PathVariable Long id) {
        log.info("Trying to get car refund record by id '{}'...", id);
        return ResponseEntity.ok().body(carsRefundsService.select(id));
    }

    @PostMapping
    public ResponseEntity<CarRefundDto> addCarRefund(@ModelAttribute CarRefundDto carRefundDto) {
        log.info("Trying to add a new car refund record...");
        return ResponseEntity.ok().body(carsRefundsService.insert(carRefundDto));
    }

    @PutMapping
    public ResponseEntity<CarRefundDto> updateCarRefund(@RequestBody CarRefundDto carRefundDto) {
        log.info("Trying to update the car refund record with id '{}'...", carRefundDto.getId());
        return ResponseEntity.ok().body(carsRefundsService.update(carRefundDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCarRefund(@PathVariable Long id) {
        log.info("Trying to delete the car refund record with id '{}'...", id);
        return ResponseEntity.ok().body(carsRefundsService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<CarRefundDto>> getAllCarRefunds() {
        log.info("Trying to get all car refunds records...");
        return ResponseEntity.ok().body(carsRefundsService.selectAll());
    }
    
}
