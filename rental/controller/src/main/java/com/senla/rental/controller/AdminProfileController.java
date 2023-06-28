package com.senla.rental.controller;

import com.senla.common.annotations.LogMethodExecution;
import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.dto.RequestDto;
import com.senla.rental.dto.RequestRejectionDto;
import com.senla.rental.dto.controller.CreateCarRefundFormDto;
import com.senla.rental.dto.controller.requests.RequestsFilterFormDto;
import com.senla.rental.service.CarsRefundsService;
import com.senla.rental.service.RequestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/profile/admin")
public class AdminProfileController {

    @Autowired
    private RequestsService requestsService;

    @Autowired
    private CarsRefundsService carsRefundsService;

    @GetMapping("/requests/{page}")
    public ResponseEntity<List<RequestDto>> showAllRequests(@PathVariable Integer page) {
        log.info("Trying to load '{}' requests page...", page);
        return ResponseEntity.ok().body(requestsService.selectPage(page, new RequestsFilterFormDto()));
    }

    @PostMapping("/requests/{page}")
    public ResponseEntity<List<RequestDto>> showAllRequests(@PathVariable Integer page,
                                                            @ModelAttribute RequestsFilterFormDto requestsFilterFormDto) {
        log.info("Trying to load '{}' requests page...", page);
        return ResponseEntity.ok().body(requestsService.selectPage(page, requestsFilterFormDto));
    }

    @GetMapping("/requests/request/{id}")
    public ResponseEntity<RequestDto> showRequestDetails(@PathVariable Long id) {
        log.info("Trying to get request details...");
        return ResponseEntity.ok().body(requestsService.select(id));
    }

    @PostMapping("/requests/request/{id}/accept")
    public ResponseEntity<RequestDto> acceptRequest(@PathVariable Long id) {
        log.info("Trying to accept car ordering request...");
        return ResponseEntity.ok().body(requestsService.acceptRequest(id));
    }

    @PostMapping("/requests/request/{id}/reject")
    public ResponseEntity<RequestDto> rejectRequest(@PathVariable Long id, @ModelAttribute RequestRejectionDto rejectionDto) {
        log.info("Trying to reject car ordering request...");
        return ResponseEntity.ok().body(requestsService.rejectRequest(id, rejectionDto));
    }

    @GetMapping("/refunds/{page}")
    public ResponseEntity<List<CarRefundDto>> getPageOfCarRefunds(@PathVariable Integer page) {
        log.info("Trying to get a refunds page...");
        return ResponseEntity.ok().body(carsRefundsService.selectRefundsPage(page));
    }

    @PostMapping("/refunds/{page}")
    public ResponseEntity<CarRefundDto> createCarRefund(@ModelAttribute CreateCarRefundFormDto createCarRefundFormDto) {
        log.info("Trying to create a new car refund record...");
        return ResponseEntity.ok().body(carsRefundsService.createCarRefund(createCarRefundFormDto));
    }

}
