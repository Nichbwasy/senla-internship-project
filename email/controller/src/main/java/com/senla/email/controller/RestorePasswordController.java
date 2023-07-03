package com.senla.email.controller;

import com.senla.email.dto.controller.NewPasswordFormDto;
import com.senla.email.service.PasswordRestoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/password/restore")
public class RestorePasswordController {

    @Autowired
    private PasswordRestoreService passwordRestoreService;

    @PostMapping("/{code}")
    public ResponseEntity<String> confirmPasswordRestore(@ModelAttribute NewPasswordFormDto formDto, @PathVariable String code) {
        log.info("Trying to confirm password restore for code '{}'...", code);
        return ResponseEntity.ok().body(passwordRestoreService.confirmPasswordRestoring(formDto, code));
    }

}
