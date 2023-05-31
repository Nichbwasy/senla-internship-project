package com.senla.authorization.controller;

import com.senla.authorization.dto.AuthorityDto;
import com.senla.authorization.service.AuthorityService;
import com.senla.common.annotations.LogMethodExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/authorization/authorities")
public class AuthoritiesController {

    @Autowired
    private AuthorityService authorityService;

    @GetMapping
    public ResponseEntity<List<AuthorityDto>> getAllAuthorities() {
        log.info("Returning all authorities...");
        return ResponseEntity.ok().body(authorityService.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorityDto> getAuthorityById(@PathVariable Long id) {
        log.info("Returning the authority with id '{}'...", id);
        return ResponseEntity.ok().body(authorityService.select(id));
    }

    @PostMapping
    public ResponseEntity<AuthorityDto> createAuthority(@ModelAttribute AuthorityDto authorityDto) {
        log.info("Trying to create a new authority...");
        return ResponseEntity.ok().body(authorityService.insert(authorityDto));
    }

    @PutMapping
    public ResponseEntity<AuthorityDto> updateAuthority(@RequestBody AuthorityDto authorityDto) {
        log.info("Updating the authority with id '{}'...", authorityDto.getId());
        return ResponseEntity.ok().body(authorityService.update(authorityDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteAuthority(@PathVariable Long id) {
        log.info("Trying to delete the authority with id '{}'...", id);
        return ResponseEntity.ok().body(authorityService.delete(id));
    }



}
