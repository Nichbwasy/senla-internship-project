package com.senla.authorization.service;

import com.senla.authorization.dao.AuthorityRepository;
import com.senla.authorization.dao.RoleRepository;
import com.senla.authorization.dto.RoleDto;
import com.senla.authorization.model.Authority;
import com.senla.authorization.model.Role;
import com.senla.authorization.service.exceptions.services.roles.RoleNotFoundException;
import com.senla.authorization.service.impl.RoleServiceImpl;
import com.senla.authorization.service.mappers.RoleMapper;
import com.senla.common.exception.repository.DeleteStatementRepositoryException;
import com.senla.common.exception.repository.EntityNotFoundException;
import com.senla.common.exception.repository.InsertStatementRepositoryException;
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
public class RoleServiceTests {

    @InjectMocks
    private RoleService roleService = new RoleServiceImpl();

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @Spy
    private RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @Test
    public void insertTest() {
        RoleDto roleDto = new RoleDto(1L, "ROLE", null);
        Mockito.lenient()
                .when(roleRepository.save(Mockito.any(Role.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(roleDto, roleService.insert(roleDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(roleRepository.save(Mockito.nullable(Role.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> roleService.insert(null));
    }

    @Test
    public void updateTest() {
        RoleDto roleDto = new RoleDto(1L, "ROLE", null);
        Role requestStatus = roleMapper.mapToModel(roleDto);

        Mockito.when(roleRepository.getReferenceById(roleDto.getId()))
                .thenReturn(requestStatus);
        Mockito.lenient()
                .when(roleRepository.save(Mockito.any(Role.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(roleDto, roleService.update(roleDto));
    }

    @Test
    public void updateNotExistedTest() {
        RoleDto roleDto = new RoleDto(1L, "ROLE", null);
        Mockito.when(roleRepository.getReferenceById(roleDto.getId()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> roleService.update(roleDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.when(roleRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> roleService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient()
                .doNothing()
                .when(roleRepository).deleteById(Mockito.anyLong());

        Assertions.assertEquals(1, roleService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .doThrow(DeleteStatementRepositoryException.class)
                .when(roleRepository).deleteById(Mockito.anyLong());

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> roleService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .doThrow(NullPointerException.class)
                .when(roleRepository).deleteById(Mockito.nullable(Long.class));

        Assertions.assertThrows(NullPointerException.class,
                () -> roleService.delete(null));
    }

    @Test
    public void selectTest() {
        Role role = new Role(1L, "ROLE", null);
        Mockito.when(roleRepository.getReferenceById(1L)).thenReturn(role);

        RoleDto roleDto = roleService.select(1L);
        Assertions.assertEquals(roleDto, roleMapper.mapToDto(role));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(roleRepository.getReferenceById(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> roleService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(roleRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> roleService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L, "ROLE1", null));
        roles.add(new Role(2L, "ROLE2", null));

        Mockito.lenient().when(roleRepository.findAll()).thenReturn(roles);

        List<RoleDto> rolesDtos = roleService.selectAll();
        Assertions.assertEquals(2, rolesDtos.size());
        for (int i = 0; i < rolesDtos.size(); i++) {
            Assertions.assertEquals(rolesDtos.get(i), roleMapper.mapToDto(roles.get(i)));
        }
    }

    @Test
    public void getRoleByNameTest() {
        Role role = new Role(1L, "ROLE", null);

        Mockito.lenient().when(roleRepository.findRoleByName("ROLE")).thenReturn(role);

        Assertions.assertEquals(roleMapper.mapToDto(role), roleService.getRoleByName("ROLE"));
    }

    @Test
    public void getRoleByNotExistedNameTest() {
        Mockito.lenient()
                .when(roleRepository.findRoleByName(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> roleService.getRoleByName("NOT_EXISTED"));
    }

    @Test
    public void getRoleByNullableNameTest() {
        Mockito.lenient()
                .when(roleRepository.findRoleByName(Mockito.nullable(String.class)))
                .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class, () -> roleService.getRoleByName(null));
    }

    @Test
    public void addAuthoritiesToRoleTest() {
        Authority authority1 = new Authority(1L, "AUTH1");
        Authority authority2 = new Authority(2L, "AUTH2");
        Role role = new Role(1L, "ROLE", new ArrayList<>());

        Mockito.when(roleRepository.existsById(1L)).thenReturn(true);
        Mockito.when(roleRepository.getReferenceById(1L)).thenReturn(role);
        Mockito.when(authorityRepository.existsById(1L)).thenReturn(true);
        Mockito.when(authorityRepository.existsById(2L)).thenReturn(true);
        Mockito.when(authorityRepository.getReferenceById(1L)).thenReturn(authority1);
        Mockito.when(authorityRepository.getReferenceById(2L)).thenReturn(authority2);

        List<Long> authIds = Arrays.asList(1L, 2L);
        List<Long> result = roleService.addAuthoritiesToRole(1L, authIds);

        Assertions.assertEquals(2, result.size());
        result.forEach(id -> Assertions.assertTrue(authIds.stream().anyMatch(aId -> aId.equals(id))));
    }

    @Test
    public void addAuthoritiesToRoleWithOneNotExistedTest() {
        Authority authority1 = new Authority(1L, "AUTH1");
        Role role = new Role(1L, "ROLE", new ArrayList<>());

        Mockito.when(roleRepository.existsById(1L)).thenReturn(true);
        Mockito.when(roleRepository.getReferenceById(1L)).thenReturn(role);
        Mockito.when(authorityRepository.existsById(1L)).thenReturn(true);
        Mockito.when(authorityRepository.getReferenceById(1L)).thenReturn(authority1);

        List<Long> authIds = Arrays.asList(1L, 2L);
        List<Long> result = roleService.addAuthoritiesToRole(1L, authIds);

        Assertions.assertEquals(1, result.size());
        result.forEach(id -> Assertions.assertTrue(authIds.stream().anyMatch(aId -> aId.equals(id))));
    }

    @Test
    public void addAuthoritiesToNotExistedRoleTest() {
        Mockito.when(roleRepository.existsById(Mockito.anyLong())).thenReturn(false);

        Assertions.assertThrows(RoleNotFoundException.class,
                () -> roleService.addAuthoritiesToRole(1L, new ArrayList<>()));
    }

    @Test
    public void addAuthoritiesToRoleNullableDataTest() {
        Mockito.when(roleRepository.existsById(Mockito.nullable(Long.class))).thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> roleService.addAuthoritiesToRole(null, null));
    }

    @Test
    public void removeAuthoritiesFromRoleTest() {
        List<Authority> authorities = Arrays.asList(new Authority(1L, "AUTH1"), new Authority(2L, "AUTH2"));
        Role role = new Role(1L, "ROLE", authorities);

        Mockito.when(roleRepository.existsById(1L)).thenReturn(true);
        Mockito.when(roleRepository.getReferenceById(1L)).thenReturn(role);

        List<Long> result = roleService.removeAuthoritiesFromRole(1L, Arrays.asList(1L, 2L));

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(0, role.getAuthorities().size());
        result.forEach(id -> Assertions.assertTrue(authorities.stream().anyMatch(a -> a.getId().equals(id))));
    }

    @Test
    public void removeAuthoritiesFromRoleIfAuthoritiesNotBelongTest() {
        List<Authority> authorities = Arrays.asList(new Authority(1L, "AUTH1"), new Authority(2L, "AUTH2"));
        Role role = new Role(1L, "ROLE", authorities);

        Mockito.when(roleRepository.existsById(1L)).thenReturn(true);
        Mockito.when(roleRepository.getReferenceById(1L)).thenReturn(role);

        List<Long> result = roleService.removeAuthoritiesFromRole(1L, Arrays.asList(1L, 3L));

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(1, role.getAuthorities().size());
        result.forEach(id -> Assertions.assertTrue(authorities.stream().anyMatch(a -> a.getId().equals(id))));
    }

    @Test
    public void removeAuthoritiesFromNotExistedRoleTest() {
        Mockito.when(roleRepository.existsById(1L)).thenReturn(false);

        Assertions.assertThrows(RoleNotFoundException.class,
                () -> roleService.removeAuthoritiesFromRole(1L, new ArrayList<>()));
    }

    @Test
    public void removeAuthoritiesFromNullableRoleTest() {
        Mockito.when(roleRepository.existsById(Mockito.nullable(Long.class)))
                .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> roleService.removeAuthoritiesFromRole(1L, new ArrayList<>()));
    }

}
