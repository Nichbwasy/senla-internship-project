package com.senla.authorization.service;

import com.senla.authorization.dao.RoleRepository;
import com.senla.authorization.dao.UserDataRepository;
import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.model.Role;
import com.senla.authorization.model.UserData;
import com.senla.authorization.service.exceptions.services.users.UserNotFoundException;
import com.senla.authorization.service.impl.UsersControlServiceImpl;
import com.senla.authorization.service.mappers.UserMapper;
import com.senla.common.constants.authorization.EmailStatuses;
import com.senla.common.exception.repository.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class UserControlServiceTests {
    
    @InjectMocks
    private UsersControlService usersControlService = new UsersControlServiceImpl();
    @Mock
    private UserDataRepository userDataRepository;
    @Mock
    private RoleRepository roleRepository;
    @Spy
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    
    @Test
    public void getUserTest() {
        UserData userData = new UserData(1L, "Login", "Password", "mail1", EmailStatuses.NOT_VERIFIED, null);
        Mockito.when(userDataRepository.getReferenceById(1L)).thenReturn(userData);

        UserDataDto user = usersControlService.getUser(1L);
        Assertions.assertEquals(userData.getId(), user.getId());
        Assertions.assertEquals(userData.getLogin(), user.getLogin());
    }

    @Test
    public void getUserNotExistedTest() {
        Mockito.when(userDataRepository.getReferenceById(Mockito.anyLong())).thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> usersControlService.getUser(1L));
    }

    @Test
    public void getUserNullableTest() {
        Mockito.when(userDataRepository.getReferenceById(Mockito.nullable(Long.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> usersControlService.getUser(1L));
    }

    @Test
    public void getAllUsersData() {
        List<UserData> userData = Arrays.asList(
                new UserData(1L, "Login", "Password", "mail2", EmailStatuses.NOT_VERIFIED, null),
                new UserData(2L, "Login2", "Password2", "mail3", EmailStatuses.NOT_VERIFIED, null)
        );

        Mockito.when(userDataRepository.findAll()).thenReturn(userData);

        Assertions.assertEquals(2, usersControlService.getAllUsersData().size());
    }

    @Test
    public void addRolesToUserTest() {
        Role role1 = new Role(1L, "ROLE1", null);
        Role role2 = new Role(2L, "ROLE2", null);
        UserData userData = new UserData(1L, "Login", "Password", "mail4", EmailStatuses.NOT_VERIFIED, new ArrayList<>());

        Mockito.when(userDataRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userDataRepository.getReferenceById(1L)).thenReturn(userData);
        Mockito.when(roleRepository.existsById(1L)).thenReturn(true);
        Mockito.when(roleRepository.existsById(2L)).thenReturn(true);
        Mockito.when(roleRepository.getReferenceById(1L)).thenReturn(role1);
        Mockito.when(roleRepository.getReferenceById(2L)).thenReturn(role2);

        List<Long> result = usersControlService.addRolesToUser(1L, Arrays.asList(1L, 2L));

        Assertions.assertEquals(2, result.size());
        result.forEach(r -> Assertions.assertTrue(userData.getRoles().stream().anyMatch(role -> role.getId().equals(r))));
    }

    @Test
    public void addRolesToUserIfNotAllRolesExistsTest() {
        Role role1 = new Role(1L, "ROLE1", null);
        UserData userData = new UserData(1L, "Login", "Password", "mail5", EmailStatuses.NOT_VERIFIED, new ArrayList<>());

        Mockito.when(userDataRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userDataRepository.getReferenceById(1L)).thenReturn(userData);
        Mockito.when(roleRepository.existsById(1L)).thenReturn(true);
        Mockito.when(roleRepository.getReferenceById(1L)).thenReturn(role1);

        List<Long> result = usersControlService.addRolesToUser(1L, Arrays.asList(1L, 2L));

        Assertions.assertEquals(1, result.size());
        result.forEach(r -> Assertions.assertTrue(userData.getRoles().stream().anyMatch(role -> role.getId().equals(r))));
    }

    @Test
    public void addRolesToUserIfUserNotExistsTest() {
        Mockito.when(userDataRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(UserNotFoundException.class,
                () -> usersControlService.addRolesToUser(1L, Arrays.asList(1L, 2L)));
    }

    @Test
    public void addRolesToUserIfUserNullableTest() {
        Mockito.when(userDataRepository.existsById(Mockito.nullable(Long.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> usersControlService.addRolesToUser(null, Arrays.asList(1L, 2L)));
    }

    @Test
    public void addRolesToUserIfRolesNullableTest() {
        Mockito.when(userDataRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userDataRepository.getReferenceById(1L)).thenReturn(new UserData());
        Mockito.when(roleRepository.existsById(Mockito.nullable(Long.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> usersControlService.addRolesToUser(1L, null));
    }

    @Test
    public void removeRolesFromUserTest() {
        List<Role> roles = Arrays.asList(
                new Role(1L, "ROLE1", null),
                new Role(2L, "ROLE2", null)
        );
        UserData userData = new UserData(1L, "Login", "Password", "mail6", EmailStatuses.NOT_VERIFIED, roles);

        Mockito.when(userDataRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userDataRepository.getReferenceById(1L)).thenReturn(userData);

        List<Long> result = usersControlService.removeRolesFromUser(1L, Arrays.asList(1L, 2L));
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(0, userData.getRoles().size());
    }

    @Test
    public void removeRolesFromUserIfNotAllRolesBelongToUserTest() {
        List<Role> roles = Arrays.asList(
                new Role(1L, "ROLE1", null),
                new Role(2L, "ROLE2", null)
        );
        UserData userData = new UserData(1L, "Login", "Password", "mail7", EmailStatuses.NOT_VERIFIED, roles);

        Mockito.when(userDataRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userDataRepository.getReferenceById(1L)).thenReturn(userData);

        List<Long> removeIds = Arrays.asList(1L, 3L);
        List<Long> result = usersControlService.removeRolesFromUser(1L, removeIds);
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, userData.getRoles().size());
        userData.getRoles().forEach(r -> Assertions.assertTrue(removeIds.stream().noneMatch(rId -> rId.equals(r.getId()))));
    }

    @Test
    public void removeRolesFromUserIfUserNotExistTest() {
        Mockito.when(userDataRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(UserNotFoundException.class,
                () -> usersControlService.removeRolesFromUser(1L, Arrays.asList(1L, 2L)));
    }

    @Test
    public void removeRolesFromUserIfNullableUserIdTest() {
        Mockito.when(userDataRepository.existsById(Mockito.nullable(Long.class)))
                .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> usersControlService.removeRolesFromUser(null, Arrays.asList(1L, 2L)));
    }

    @Test
    public void removeRolesFromUserIfNullableRolesIdsTest() {
        Mockito.when(userDataRepository.existsById(1L)).thenReturn(true);
        Mockito.when(userDataRepository.getReferenceById(1L)).thenReturn(new UserData());

        Assertions.assertThrows(NullPointerException.class,
                () -> usersControlService.removeRolesFromUser(1L, null));
    }
}
