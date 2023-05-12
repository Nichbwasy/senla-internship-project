package com.senla.authorization.controller;

import com.senla.authorization.dto.registrations.JwtTokensResponseDto;
import com.senla.authorization.dto.registrations.LogInUserDto;
import com.senla.authorization.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/authorization")
public class AuthorizationController {

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping
    private ResponseEntity<JwtTokensResponseDto> logInUser(@ModelAttribute LogInUserDto dto) {
        log.info("Trying to log in user '{}'...", dto.getLogin());
        return ResponseEntity.ok().body(authorizationService.logInUser(dto));
    }

}
