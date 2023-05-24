package com.senla.authorization.service;

import com.senla.authorization.dto.RoleDto;

import java.util.List;

public interface RoleService {

    RoleDto insert(RoleDto roleDto);
    RoleDto update(RoleDto roleDto);
    RoleDto select(Long id);
    Long delete(Long id);
    List<RoleDto> selectAll();

    List<Long> addAuthoritiesToRole(Long roleId, List<Long> authoritiesIds);
    List<Long> removeAuthoritiesFromRole(Long roleId, List<Long> authoritiesIds);
    RoleDto getRoleByName(String name);

}
