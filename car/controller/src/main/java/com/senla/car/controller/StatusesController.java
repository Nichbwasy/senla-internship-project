package com.senla.car.controller;

import com.senla.car.dto.StatusDto;
import com.senla.car.service.StatusesService;
import com.senla.common.annotations.LogMethodExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/statuses")
public class StatusesController {

    @Autowired
    private StatusesService statusesService;

    @GetMapping("/{id}")
    public ResponseEntity<StatusDto> getStatus(@PathVariable Long id) {
        log.info("Trying to get a car status with id '{}'...", id);
        return ResponseEntity.ok().body(statusesService.select(id));
    }

    @PostMapping
    public ResponseEntity<StatusDto> addStatus(@ModelAttribute StatusDto statusDto) {
        log.info("Trying to add a new car status '{}'...", statusDto);
        return ResponseEntity.ok().body(statusesService.insert(statusDto));
    }

    @PutMapping
    public ResponseEntity<StatusDto> updateStatus(@RequestBody StatusDto statusDto) {
        log.info("Trying to update the car status with id '{}'...", statusDto.getId());
        return ResponseEntity.ok().body(statusesService.update(statusDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteStatus(@PathVariable Long id) {
        log.info("Trying to delete the car status with id '{}'...", id);
        return ResponseEntity.ok().body(statusesService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<StatusDto>> getAllStatuses() {
        log.info("Trying to get all car statuses...");
        return ResponseEntity.ok().body(statusesService.selectAll());
    }
}
