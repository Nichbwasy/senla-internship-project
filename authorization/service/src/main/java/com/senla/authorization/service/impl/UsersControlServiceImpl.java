package com.senla.authorization.service.impl;

import com.senla.authorization.dao.RoleRepository;
import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.model.Role;
import com.senla.authorization.model.UserData;
import com.senla.authorization.service.UsersControlService;
import com.senla.authorization.service.exceptions.services.users.UserNotFoundException;
import com.senla.authorization.service.mappers.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class UsersControlServiceImpl implements UsersControlService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDataDto getUser(Long id) {
        log.info("Trying to get user with id '{}'...", id);
        return userMapper.mapToDto(userDataRepository.getReferenceById(id));
    }

    @Override
    public List<UserDataDto> getAllUsersData() {
        log.info("Trying to get list of all users...");
        return userDataRepository
                .findAll()
                .stream()
                .map(u -> userMapper.mapToDto(u))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Long> addRolesToUser(Long userId, List<Long> rolesIds) {
        log.info("Trying to add roles '{}' to the user '{}'...", rolesIds, userId);
        List<Long> addedRoles = new ArrayList<>();

        checkIfUserExists(userId);

        UserData user = userDataRepository.getReferenceById(userId);
        rolesIds.forEach(rId -> {
            if (roleRepository.existsById(rId)) {
                user.getRoles().add(roleRepository.getReferenceById(rId));
                addedRoles.add(rId);
                log.info("Role with id '{}' has been added to the user '{}'.", rId, userId);
            } else {
                log.warn("Unable find role with id '{}'!", rId);
            }
        });

        return addedRoles;
    }

    @Override
    @Transactional
    public List<Long> removeRolesFromUser(Long userId, List<Long> rolesIds) {
        log.info("Trying to remove roles '{}' from the user '{}'...", rolesIds, userId);
        List<Long> removedRoles = new ArrayList<>();

        checkIfUserExists(userId);

        UserData user = userDataRepository.getReferenceById(userId);

        ArrayList<Role> roles = new ArrayList<>(user.getRoles());

        roles.removeIf(r -> {
            if (rolesIds.stream().anyMatch(rId -> r.getId().equals(rId))) {
                log.info("Role with id '{}' has been removed from the user '{}'", r.getId(), userId);
                removedRoles.add(r.getId());
                return true;
            }
            log.warn("Role with id '{}' doesn't belong to the user '{}'!", r.getId(), userId);
            return false;
        });

        user.setRoles(roles);
        return removedRoles;
    }

    private void checkIfUserExists(Long userId) {
        if(!userDataRepository.existsById(userId)) {
            log.info("Unable find user with id '{}'!", userId);
            throw new UserNotFoundException(String.format("Unable find user with id '%s'!", userId));
        }
    }

}
