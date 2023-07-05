package com.senla.email.controller;

import com.senla.email.service.EmailConfirmNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/mailing/confirmation")
public class ConfirmEmailController {

    @Autowired
    private EmailConfirmNotificationService confirmNotificationService;

    @GetMapping("/{code}")
    public ResponseEntity<String> confirmEmailByVerificationCode(@PathVariable String code) {
        log.info("Trying to confirm email with code '{}'...", code);
        confirmNotificationService.confirmEmailVerification(code);
        return ResponseEntity.ok().body("Success!");
    }

}
