package com.senla.rental.controller;

import com.senla.payment.dto.CarRentalReceiptDto;
import com.senla.rental.service.RequestsPaymentService;
import com.senla.starter.jwt.security.utils.utils.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/profile")
public class PayRequestController {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private RequestsPaymentService requestsPaymentService;

    @PostMapping("/request/payment/{requestId}")
    public ResponseEntity<CarRentalReceiptDto> payRequest(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                                          @PathVariable Long requestId) {
        log.info("Trying to paying the request with id '{}'...", requestId);
        Long userId = jwtTokenUtils.getAccessTokenUserId(authorization.substring(7));
        return ResponseEntity.ok().body(requestsPaymentService.payRequest(userId, requestId));
    }

}
