package com.senla.authorization.service;

import com.senla.authorization.dto.RoleDto;

import java.util.List;

public interface RoleService {

    /**
     * Inserts a new role record
     * @param roleDto Role to insert
     * @return Inserted record
     */
    RoleDto insert(RoleDto roleDto);

    /**
     * Updates a role record with id specified in dto
     * @param roleDto Role to update
     * @return Updated role
     */
    RoleDto update(RoleDto roleDto);

    /**
     * Gets role record by id
     * @param id Id of role to get
     * @return Selected role
     */
    RoleDto select(Long id);

    /**
     * Removes role record by id
     * @param id Id of role to delete
     * @return Deleted role's id
     */
    Long delete(Long id);

    /**
     * Returns all roles record
     * @return All roles record
     */
    List<RoleDto> selectAll();

    /**
     * Adds authorities (if its exists) to role
     * @param roleId Role id to add authorities
     * @param authoritiesIds List of adding authorities
     * @return List of ids added authorities
     */
    List<Long> addAuthoritiesToRole(Long roleId, List<Long> authoritiesIds);

    /**
     * Removes authorities (if its exists) from role
     * @param roleId Role id to remove authorities
     * @param authoritiesIds List of removing authorities
     * @return List of ids removed authorities
     */
    List<Long> removeAuthoritiesFromRole(Long roleId, List<Long> authoritiesIds);

    /**
     * Returns role by its name
     * @param name Roles name
     * @return Selected role record
     */
    RoleDto getRoleByName(String name);

}
