package com.senla.authorization.controller;

import com.senla.authorization.dto.RoleDto;
import com.senla.authorization.dto.controllers.AddOrRemoveAuthoritiesDto;
import com.senla.authorization.service.RoleService;
import com.senla.common.annotations.LogMethodExecution;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@LogMethodExecution
@RequestMapping("/authorization/roles")
public class RolesController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.selectAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> getRole(@PathVariable Long id) {
        log.info("Trying to get the role with id '{}'...", id);
        return ResponseEntity.ok().body(roleService.select(id));
    }

    @PostMapping
    public ResponseEntity<RoleDto> addRole(@ModelAttribute RoleDto roleDto) {
        log.info("Trying to add a new role '{}'...", roleDto);
        return ResponseEntity.ok().body(roleService.insert(roleDto));
    }

    @PutMapping
    public ResponseEntity<RoleDto> updateRole(@RequestBody RoleDto roleDto) {
        log.info("Trying to update the role with id '{}'...", roleDto.getId());
        return ResponseEntity.ok().body(roleService.update(roleDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteRole(@PathVariable Long id) {
        log.info("Trying to delete the role with id '{}'...", id);
        return ResponseEntity.ok().body(roleService.delete(id));
    }

    @PostMapping("/authorities/adding")
    public ResponseEntity<List<Long>> addAuthorityToRole(@ModelAttribute AddOrRemoveAuthoritiesDto dto) {
        log.info("Trying to add authorities with ids '{}' to the role with id '{}'..."
                , dto.getAuthoritiesIds(), dto.getRoleId());
        return ResponseEntity.ok().body(roleService.addAuthoritiesToRole(
                dto.getRoleId(),
                dto.getAuthoritiesIds()
        ));
    }

    @PostMapping("/authorities/removal")
    public ResponseEntity<List<Long>> removeAuthorityToRole(@ModelAttribute AddOrRemoveAuthoritiesDto dto) {
        log.info("Trying to remove authorities with ids '{}' from the role with id '{}'..."
                , dto.getAuthoritiesIds(), dto.getRoleId());
        return ResponseEntity.ok().body(roleService.removeAuthoritiesFromRole(
                dto.getRoleId(),
                dto.getAuthoritiesIds()
        ));
    }
}
