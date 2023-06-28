package com.senla.rental.controller;

import com.senla.common.annotations.LogMethodExecution;
import com.senla.rental.dto.RequestRejectionDto;
import com.senla.rental.service.RequestRejectionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/requests/rejections")
public class RequestRejectionsController {

    @Autowired
    private RequestRejectionsService requestRejectionsService;

    @GetMapping("/{id}")
    public ResponseEntity<RequestRejectionDto> getRequestRejection(@PathVariable Long id) {
        log.info("Trying to get request rejection with id '{}'...", id);
        return ResponseEntity.ok().body(requestRejectionsService.select(id));
    }

    @PostMapping
    public ResponseEntity<RequestRejectionDto> addRequestRejection(@ModelAttribute RequestRejectionDto requestRejectionDto) {
        log.info("Trying to add a new request rejection...");
        return ResponseEntity.ok().body(requestRejectionsService.insert(requestRejectionDto));
    }

    @PutMapping
    public ResponseEntity<RequestRejectionDto> updateRequestRejection(@RequestBody RequestRejectionDto requestRejectionDto) {
        log.info("Trying to update the request rejection with id '{}'...", requestRejectionDto.getId());
        return ResponseEntity.ok().body(requestRejectionsService.update(requestRejectionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRequestRejection(@PathVariable Long id) {
        log.info("Trying to delete the request rejection with id '{}'...", id);
        return ResponseEntity.ok().body(requestRejectionsService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<RequestRejectionDto>> getAllRequestRejections() {
        log.info("Trying to get all request rejections records...");
        return ResponseEntity.ok().body(requestRejectionsService.selectAll());
    }
}
