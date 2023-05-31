package com.senla.car.controller;

import com.senla.car.dto.TypeDto;
import com.senla.car.service.TypesService;
import com.senla.common.annotations.LogMethodExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/types")
public class TypesController {

    @Autowired
    private TypesService typesService;

    @GetMapping("/{id}")
    public ResponseEntity<TypeDto> getType(@PathVariable Long id) {
        log.info("Trying to get the car type with id '{}'...", id);
        return ResponseEntity.ok().body(typesService.select(id));
    }

    @PostMapping
    public ResponseEntity<TypeDto> addType(@ModelAttribute TypeDto typeDto) {
        log.info("Trying to add a new car type '{}'...", typeDto);
        return ResponseEntity.ok().body(typesService.insert(typeDto));
    }

    @PutMapping
    public ResponseEntity<TypeDto> updateType(@RequestBody TypeDto typeDto) {
        log.info("Trying to update the car type with id '{}'...", typeDto.getId());
        return ResponseEntity.ok().body(typesService.update(typeDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        log.info("Trying to delete the car type with id '{}'...", id);
        return ResponseEntity.ok().body(typesService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<TypeDto>> getAll() {
        log.info("Trying to get all car types...");
        return ResponseEntity.ok().body(typesService.selectAll());
    }

}
