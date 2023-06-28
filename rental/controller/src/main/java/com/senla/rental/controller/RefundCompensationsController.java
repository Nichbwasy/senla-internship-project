package com.senla.rental.controller;

import com.senla.common.annotations.LogMethodExecution;
import com.senla.rental.dto.RefundCompensationDto;
import com.senla.rental.service.RefundCompensationsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/car/refunds/compensations")
public class RefundCompensationsController {

    @Autowired
    private RefundCompensationsService refundCompensationsService;

    @GetMapping("/{id}")
    public ResponseEntity<RefundCompensationDto> getRefundCompensation(@PathVariable Long id) {
        log.info("Trying to get refund compensation record with id '{}'...", id);
        return ResponseEntity.ok().body(refundCompensationsService.select(id));
    }

    @PostMapping
    public ResponseEntity<RefundCompensationDto> addRefundCompensation(@ModelAttribute RefundCompensationDto refundCompensationDto) {
        log.info("Trying to add a new refund compensation record...");
        return ResponseEntity.ok().body(refundCompensationsService.insert(refundCompensationDto));
    }

    @PutMapping
    public ResponseEntity<RefundCompensationDto> updateRefundCompensation(@RequestBody RefundCompensationDto refundCompensationDto) {
        log.info("Trying to update the refund compensation record with id '{}'...", refundCompensationDto.getId());
        return ResponseEntity.ok().body(refundCompensationsService.update(refundCompensationDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRefundCompensation(@PathVariable Long id) {
        log.info("Trying to delete the refund compensation with id '{}'...", id);
        return ResponseEntity.ok().body(refundCompensationsService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<RefundCompensationDto>> getAllRefundCompensations() {
        log.info("Trying to get all refund compensations...");
        return ResponseEntity.ok().body(refundCompensationsService.selectAll());
    }
}
