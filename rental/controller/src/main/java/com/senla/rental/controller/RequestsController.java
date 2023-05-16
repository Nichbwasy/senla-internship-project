package com.senla.rental.controller;

import com.senla.rental.dto.RequestDto;
import com.senla.rental.service.RequestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/requests")
public class RequestsController {

    @Autowired
    private RequestsService requestsService;

    @GetMapping("/{id}")
    public ResponseEntity<RequestDto> getRequest(@PathVariable Long id) {
        log.info("Trying to get request with id '{}'...", id);
        return ResponseEntity.ok().body(requestsService.select(id));
    }

    @PostMapping
    public ResponseEntity<RequestDto> addRequest(@ModelAttribute RequestDto requestDto) {
        log.info("Trying to add a new request...");
        return ResponseEntity.ok().body(requestsService.insert(requestDto));
    }

    @PutMapping
    public ResponseEntity<RequestDto> updateRequest(@RequestBody RequestDto requestDto) {
        log.info("Trying to update the request with id '{}'...", requestDto.getId());
        return ResponseEntity.ok().body(requestsService.update(requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRequest(@PathVariable Long id) {
        log.info("Trying to delete request with id '{}'...", id);
        return ResponseEntity.ok().body(requestsService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        log.info("Trying to get all requests...");
        return ResponseEntity.ok().body(requestsService.selectAll());
    }
}
