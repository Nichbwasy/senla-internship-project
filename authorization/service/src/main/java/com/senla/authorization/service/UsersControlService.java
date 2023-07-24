package com.senla.authorization.service;

import com.senla.authorization.dto.UserDataDto;

import java.util.List;

public interface UsersControlService {

    /**
     * Returns user record by id
     * @param id Id of user record
     * @return Selected user record
     */
    UserDataDto getUser(Long id);

    /**
     * Returns records of all users
     * @return List of all users record
     */
    List<UserDataDto> getAllUsersData();

    /**
     * Adds a new roles to the user
     * @param userId Id of user to add a roles
     * @param rolesIds List of roles ids
     * @return List of added roles ids
     */
    List<Long> addRolesToUser(Long userId, List<Long> rolesIds);

    /**
     * Removes roles from the user
     * @param userId Id of user to remove a roles
     * @param rolesIds List of roles ids
     * @return List of removed roles ids
     */
    List<Long> removeRolesFromUser(Long userId, List<Long> rolesIds);
}
