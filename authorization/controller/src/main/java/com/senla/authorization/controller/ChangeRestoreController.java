package com.senla.authorization.controller;

import com.senla.authorization.dto.controllers.CreatePasswordRestoreRequestDto;
import com.senla.authorization.service.PasswordRestoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/password/restore")
public class ChangeRestoreController {

    @Autowired
    private PasswordRestoreService passwordRestoreService;

    @PostMapping
    public ResponseEntity<String> createPasswordRestoreRequest(@ModelAttribute CreatePasswordRestoreRequestDto dto) {
        log.info("Trying to create password restore request for user '{}'...", dto.getLogin());
        return ResponseEntity.ok().body(passwordRestoreService.createRestorePasswordRequest(dto));
    }

}
