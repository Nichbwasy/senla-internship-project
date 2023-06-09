package com.senla.payment.controller;

import com.senla.payment.dto.CarRentalReceiptDto;
import com.senla.payment.dto.clients.AcceptPaymentDto;
import com.senla.payment.service.CarRentalReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/payment")
public class AcceptPaymentController {

    @Autowired
    private CarRentalReceiptService carRentalReceiptService;

    @PostMapping("/acceptation")
    public ResponseEntity<CarRentalReceiptDto> acceptPayment(@ModelAttribute AcceptPaymentDto acceptPaymentDto) {
        log.info("Trying to accept payment for request '{}'...", acceptPaymentDto.getRequestDto().getId());
        return ResponseEntity.ok().body(carRentalReceiptService.acceptPayment(acceptPaymentDto));
    }

}
