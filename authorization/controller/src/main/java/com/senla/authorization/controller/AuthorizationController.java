package com.senla.authorization.controller;

import com.senla.authorization.dto.registrations.JwtTokensResponseDto;
import com.senla.authorization.dto.registrations.LogInUserDto;
import com.senla.authorization.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping
    private ResponseEntity<String> logInUser() {
        log.info("Trying to show log in page...");
        return ResponseEntity.ok().body("Hello, this is login page!");
    }

    @PostMapping
    private ResponseEntity<JwtTokensResponseDto> logInUser(@ModelAttribute LogInUserDto dto) {
        log.info("Trying to log in user '{}'...", dto.getLogin());
        return ResponseEntity.ok().body(authorizationService.logInUser(dto));
    }

}
