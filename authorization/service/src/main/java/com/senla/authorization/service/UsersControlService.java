package com.senla.authorization.service;

import com.senla.authorization.dto.UserDataDto;

import java.util.List;

public interface UsersControlService {

    UserDataDto getUser(Long id);
    List<UserDataDto> getAllUsersData();
    List<Long> addRolesToUser(Long userId, List<Long> rolesIds);
    List<Long> removeRolesFromUser(Long userId, List<Long> rolesIds);
}
