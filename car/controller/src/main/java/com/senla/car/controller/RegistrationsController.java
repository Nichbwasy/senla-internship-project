package com.senla.car.controller;

import com.senla.car.dto.RegistrationDto;
import com.senla.car.service.RegistrationsService;
import com.senla.common.annotations.LogMethodExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/registrations")
public class RegistrationsController {

    @Autowired
    private RegistrationsService registrationsService;

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDto> getRegistration(@PathVariable Long id) {
        log.info("Trying to get car registration by id '{}'...", id);
        return ResponseEntity.ok().body(registrationsService.select(id));
    }

    @PostMapping
    public ResponseEntity<RegistrationDto> addRegistration(@ModelAttribute RegistrationDto registrationDto) {
        log.info("Trying to add a new car registration '{}'...", registrationDto);
        return ResponseEntity.ok().body(registrationsService.insert(registrationDto));
    }

    @PutMapping
    public ResponseEntity<RegistrationDto> updateRegistration(@RequestBody RegistrationDto registrationDto) {
        log.info("Trying to update the car registration with id '{}'...", registrationDto.getId());
        return ResponseEntity.ok().body(registrationsService.update(registrationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRegistration(@PathVariable Long id) {
        log.info("Trying to delete the car registration with id '{}'...", id);
        return ResponseEntity.ok().body(registrationsService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<RegistrationDto>> getAllRegistrations() {
        log.info("Trying to get all car registrations...");
        return ResponseEntity.ok().body(registrationsService.selectAll());
    }
}
