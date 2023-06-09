package com.senla.rental.controller;

import com.senla.common.annotations.LogMethodExecution;
import com.senla.rental.dto.RequestStatusDto;
import com.senla.rental.service.RequestStatusesService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/requests/statuses")
public class RequestStatusesController {

    @Autowired
    private RequestStatusesService requestStatusesService;

    @GetMapping("/{id}")
    public ResponseEntity<RequestStatusDto> getRequestStatus(@PathVariable Long id) {
        log.info("Trying to get request status with id '{}'...", id);
        return ResponseEntity.ok().body(requestStatusesService.select(id));
    }

    @PostMapping
    public ResponseEntity<RequestStatusDto> addRequestStatus(@ModelAttribute RequestStatusDto requestStatusDto) {
        log.info("Trying to add a new request status...");
        return ResponseEntity.ok().body(requestStatusesService.insert(requestStatusDto));
    }

    @PutMapping
    public ResponseEntity<RequestStatusDto> updateRequestStatus(@RequestBody RequestStatusDto requestStatusDto) {
        log.info("Trying to update the request status with id '{}'...", requestStatusDto.getId());
        return ResponseEntity.ok().body(requestStatusesService.update(requestStatusDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRequestStatus(@PathVariable Long id) {
        log.info("Trying to delete the request status with id '{}'...", id);
        return ResponseEntity.ok().body(requestStatusesService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<RequestStatusDto>> getAllRequestStatus() {
        log.info("Trying to get all request statuses records...");
        return ResponseEntity.ok().body(requestStatusesService.selectAll());
    }

    @GetMapping("/status")
    public ResponseEntity<RequestStatusDto> getRequestStatusByName(@PathParam("name") String name) {
        log.info("Trying to find request status with name '{}'...", name);
        return ResponseEntity.ok().body(requestStatusesService.selectByName(name));
    }
}
