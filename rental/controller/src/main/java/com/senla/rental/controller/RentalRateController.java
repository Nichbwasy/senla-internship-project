package com.senla.rental.controller;

import com.senla.common.annotations.LogMethodExecution;
import com.senla.rental.dto.RentalRateDto;
import com.senla.rental.service.RentalRateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rates")
@LogMethodExecution
public class RentalRateController {

    @Autowired
    private RentalRateService rentalRateService;

    @GetMapping
    public ResponseEntity<List<RentalRateDto>> getAllRates() {
        log.info("Trying to get all rental rates...");
        return ResponseEntity.ok().body(rentalRateService.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalRateDto> getRentalRate(@PathVariable Long id) {
        log.info("Trying to get rental rate with id '{}'...", id);
        return ResponseEntity.ok().body(rentalRateService.select(id));
    }

    @PostMapping
    public ResponseEntity<RentalRateDto> createRentalRate(@ModelAttribute RentalRateDto rentalRateDto) {
        log.info("Trying to create a new rental rate...");
        return ResponseEntity.ok().body(rentalRateService.insert(rentalRateDto));
    }

    @PutMapping
    public ResponseEntity<RentalRateDto> updateRentalRate(@RequestBody RentalRateDto rentalRateDto) {
        log.info("Trying to update rental rate record with id '{}'...", rentalRateDto.getId());
        return ResponseEntity.ok().body(rentalRateService.update(rentalRateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRentalRate(@PathVariable Long id) {
        log.info("Trying to remove rental rate record with id '{}'...", id);
        return ResponseEntity.ok().body(rentalRateService.delete(id));
    }

}
