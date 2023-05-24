package com.senla.authorization.service.impl;

import com.senla.authorization.dao.AuthorityRepository;
import com.senla.authorization.dao.RoleRepository;
import com.senla.authorization.dto.RoleDto;
import com.senla.authorization.model.Authority;
import com.senla.authorization.model.Role;
import com.senla.authorization.service.RoleService;
import com.senla.authorization.service.exceptions.services.roles.RoleNotFoundException;
import com.senla.authorization.service.mappers.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    @Transactional
    public RoleDto insert(RoleDto roleDto) {
        Role role = roleMapper.mapToModel(roleDto);
        log.info("Trying to insert new role '{}'...", role);
        return roleMapper.mapToDto(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleDto update(RoleDto roleDto) {
        Role role = roleRepository.getReferenceById(roleDto.getId());
        log.info("Trying update a role with id '{}' to '{}'...", roleDto.getId(), roleDto);
        roleMapper.updateRole(roleDto, role);
        return roleMapper.mapToDto(roleRepository.save(role));
    }

    @Override
    public RoleDto select(Long id) {
        log.info("Trying to ger role with id '{}'...", id);
        Role role = roleRepository.getReferenceById(id);
        return roleMapper.mapToDto(role);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        roleRepository.deleteById(id);
        return id;
    }

    @Override
    public List<RoleDto> selectAll() {
        log.info("Trying to get list of all roles...");
        return roleRepository.findAll()
                .stream()
                .map(r -> roleMapper.mapToDto(r))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public List<Long> addAuthoritiesToRole(Long roleId, List<Long> authoritiesIds) {
        log.info("Trying to add list of authorities '{}' to the role with id '{}'...", authoritiesIds, roleId);
        List<Long> addedAuthorities = new ArrayList<>();

        checkRoleExistence(roleId);

        Role role = roleRepository.getReferenceById(roleId);
        authoritiesIds.forEach(aId -> {
            if (authorityRepository.existsById(aId)) {
                role.getAuthorities().add(authorityRepository.getReferenceById(aId));
                addedAuthorities.add(aId);
                log.info("Authority '{}' has been added to the role '{}'.", aId, roleId);
            } else {
                log.warn("Unable to find authority with id '{}'!", aId);
            }
        });

        return addedAuthorities;
    }

    @Override
    @Transactional
    public List<Long> removeAuthoritiesFromRole(Long roleId, List<Long> authoritiesIds) {
        log.info("Trying to remove list of authorities '{}' from the role with id '{}'...", authoritiesIds, roleId);
        List<Long> removedAuthoritiesIds = new ArrayList<>();

        checkRoleExistence(roleId);

        Role role = roleRepository.getReferenceById(roleId);

        ArrayList<Authority> authorities = new ArrayList<>(role.getAuthorities());

        authorities.removeIf(auth -> {
            if (authoritiesIds.stream().anyMatch(aId -> auth.getId().equals(aId))) {
                log.info("Authority '{}' has been removed from role '{}'.", auth.getId(), roleId);
                removedAuthoritiesIds.add(auth.getId());
                return true;
            } else {
                log.warn("Can't remove the authority '{}'. Authority doesn't belong ti=o the role '{}'!", auth.getId(), roleId);
                return false;
            }
        });

        role.setAuthorities(authorities);
        return removedAuthoritiesIds;
    }

    private void checkRoleExistence(Long roleId) {
        if (!roleRepository.existsById(roleId)) {
            log.warn("Unable to find role with id '{}'!", roleId);
            throw new RoleNotFoundException(
                    String.format("Unable to find role with id '%s'!", roleId)
            );
        }
    }

    @Override
    public RoleDto getRoleByName(String name) {
        log.info("Trying to get role with name '{}'...", name);
        Role role = roleRepository.findRoleByName(name);
        return roleMapper.mapToDto(role);
    }
}
