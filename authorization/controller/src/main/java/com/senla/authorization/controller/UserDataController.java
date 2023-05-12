package com.senla.authorization.controller;

import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.service.UsersControlService;
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
@RequestMapping("/users")
public class UserDataController {

    @Autowired
    private UsersControlService usersControlService;

    @GetMapping("/{id}")
    private ResponseEntity<UserDataDto> getUserDataById(@PathVariable Long id) {
        log.info("Trying to get user data...");
        return ResponseEntity.ok().body(usersControlService.getUser(id));
    }

    @GetMapping
    private ResponseEntity<List<UserDataDto>> getAllUsersData() {
        log.info("Trying to get all data of all users...");
        return ResponseEntity.ok().body(usersControlService.getAllUsersData());
    }

}
