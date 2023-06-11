package com.senla.car.controller;

import com.senla.car.dto.ConditionDto;
import com.senla.car.service.ConditionsService;
import com.senla.common.annotations.LogMethodExecution;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/conditions")
public class ConditionsController {

    @Autowired
    private ConditionsService conditionsService;

    @GetMapping("/{id}")
    public ResponseEntity<ConditionDto> getCondition(@PathVariable Long id) {
        log.info("Trying to get car condition by id '{}'...", id);
        return ResponseEntity.ok().body(conditionsService.select(id));
    }

    @PostMapping
    public ResponseEntity<ConditionDto> addCondition(@ModelAttribute ConditionDto conditionDto) {
        log.info("Trying to add a new car condition '{}'...", conditionDto);
        return ResponseEntity.ok().body(conditionsService.insert(conditionDto));
    }

    @PutMapping
    public ResponseEntity<ConditionDto> updateCondition(@RequestBody ConditionDto conditionDto) {
        log.info("Trying to update the car condition with id '{}'...", conditionDto.getId());
        return ResponseEntity.ok().body(conditionsService.update(conditionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteCondition(@PathVariable Long id) {
        log.info("Trying to delete the car condition with id '{}'...", id);
        return ResponseEntity.ok().body(conditionsService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<ConditionDto>> getAllCondition() {
        log.info("Trying to get all car conditions...");
        return ResponseEntity.ok().body(conditionsService.selectAll());
    }

    @GetMapping("/existence")
    public ResponseEntity<Boolean> existsByName(@PathParam("name") String name) {
        log.info("Trying to check cat condition existence with name '{}'...", name);
        return ResponseEntity.ok().body(conditionsService.existsByName(name));
    }
}
