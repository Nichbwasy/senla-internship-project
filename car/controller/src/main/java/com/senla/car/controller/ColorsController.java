package com.senla.car.controller;

import com.senla.car.dto.ColorDto;
import com.senla.car.service.ColorsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/colors")
public class ColorsController {

    @Autowired
    private ColorsService colorsService;
    
    @GetMapping("/{id}")
    public ResponseEntity<ColorDto> getColor(@PathVariable Long id) {
        log.info("Trying to get the car color by idd '{}'...", id);
        return ResponseEntity.ok().body(colorsService.select(id));
    }

    @PostMapping
    public ResponseEntity<ColorDto> addColor(@ModelAttribute ColorDto colorDto) {
        log.info("Trying to save a new car color '{}'...", colorDto);
        return ResponseEntity.ok().body(colorsService.insert(colorDto));
    }

    @PutMapping
    public ResponseEntity<ColorDto> updateColor(@RequestBody ColorDto colorDto) {
        log.info("Trying to update the car color with id '{}'...", colorDto.getId());
        return ResponseEntity.ok().body(colorsService.update(colorDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteColor(@PathVariable Long id) {
        log.info("Trying to delete the car color by id '{}'...", id);
        return ResponseEntity.ok().body(colorsService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<ColorDto>> getAllColors() {
        log.info("Trying to get all car colors...");
        return ResponseEntity.ok().body(colorsService.selectAll());
    }
}
