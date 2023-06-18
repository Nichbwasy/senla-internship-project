package com.senla.payment.controller;

import com.senla.payment.dto.PaymentReceiptDto;
import com.senla.payment.dto.PaymentRequestDto;
import com.senla.payment.service.PaymentRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/payment")
public class PaymentRequestsManagementController {

    @Autowired
    private PaymentRequestService paymentRequestService;

    @GetMapping("/requests/{page}")
    public ResponseEntity<List<PaymentRequestDto>> showPaymentRequests(@PathVariable Integer page) {
        log.info("Trying to show page '{}' of payment requests...", page);
        return ResponseEntity.ok().body(paymentRequestService.getPaymentRequestPage(page));
    }

    @GetMapping("/requests/request/{id}")
    public ResponseEntity<PaymentRequestDto> showPaymentRequest(@PathVariable String id) {
        log.info("Trying to get payment with id '{}'...", id);
        return ResponseEntity.ok().body(paymentRequestService.getPayment(id));
    }

    @PostMapping("/requests/request/{id}/acceptation")
    public ResponseEntity<PaymentReceiptDto> acceptPayment(@PathVariable String id) {
        log.info("Trying to accept request with id '{}'...", id);
        return ResponseEntity.ok().body(paymentRequestService.acceptPaymentRequest(id));
    }

}
