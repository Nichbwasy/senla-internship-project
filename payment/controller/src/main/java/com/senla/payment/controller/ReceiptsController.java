package com.senla.payment.controller;

import com.senla.payment.dto.CarRentalReceiptDto;
import com.senla.payment.service.CarRentalReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/receipts")
public class ReceiptsController {

    @Autowired
    private CarRentalReceiptService carRentalReceiptService;

    @GetMapping
    public ResponseEntity<List<CarRentalReceiptDto>> getAllCarRentalReceipts() {
        log.info("Trying to get all receipts...");
        return ResponseEntity.ok().body(carRentalReceiptService.getAllCarRentalReceipt());
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<CarRentalReceiptDto> getCarRentalReceipt(@PathVariable Long id) {
        log.info("Trying to get all receipts...");
        return ResponseEntity.ok().body(carRentalReceiptService.getCarRentalReceipt(id));
    }

    @GetMapping("/user/{userId}/page/{page}")
    public ResponseEntity<List<CarRentalReceiptDto>> getCarRentalReceiptsPage(
            @PathVariable Long userId,
            @PathVariable Integer page
    ) {
        log.info("Trying to get all receipts...");
        return ResponseEntity.ok().body(carRentalReceiptService.getUserCarRentalReceiptsPage(userId, page));
    }

}
