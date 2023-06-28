package com.senla.email.controller;

import com.senla.email.dto.MailingRequestDto;
import com.senla.email.service.MailingRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mailing/requests")
public class MailingRequestController {

    @Autowired
    private MailingRequestService mailingRequestService;

    @GetMapping("/{page}")
    public ResponseEntity<List<MailingRequestDto>> showMailingRequestsPage(@PathVariable Integer page) {
        log.info("Trying to show mailing request {} page...", page);
        return ResponseEntity.ok().body(mailingRequestService.showMailingRequestsPage(page));
    }


}
