package com.senla.authorization.controller;

import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.dto.registrations.UserRegistrationDataDto;
import com.senla.authorization.service.RegistrationService;
import com.senla.common.annotations.LogMethodExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/authorization/register")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<UserDataDto> registerUser(UserRegistrationDataDto dto) {
        log.info("Trying to register a new user '{}'...", dto.getLogin());
        return ResponseEntity.ok().body(registrationService.registerUser(dto));
    }

}
