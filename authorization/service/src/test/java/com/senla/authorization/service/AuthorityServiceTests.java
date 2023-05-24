package com.senla.authorization.service;

import com.senla.authorization.dao.AuthorityRepository;
import com.senla.authorization.dto.AuthorityDto;
import com.senla.authorization.model.Authority;
import com.senla.authorization.service.impl.AuthorityServiceImpl;
import com.senla.authorization.service.mappers.AuthorityMapper;
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
import java.util.List;

@ExtendWith(SpringExtension.class)
public class AuthorityServiceTests {

    @InjectMocks
    private AuthorityService authorityService = new AuthorityServiceImpl();
    
    @Mock
    private AuthorityRepository authorityRepository;

    @Spy
    private AuthorityMapper authorityMapper = Mappers.getMapper(AuthorityMapper.class);

    @Test
    public void insertTest() {
        AuthorityDto authorityDto = new AuthorityDto(1L, "AUTHORITY");
        Mockito.lenient()
                .when(authorityRepository.save(Mockito.any(Authority.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(authorityDto, authorityService.insert(authorityDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(authorityRepository.save(Mockito.nullable(Authority.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> authorityService.insert(null));
    }

    @Test
    public void updateTest() {
        AuthorityDto authorityDto = new AuthorityDto(1L, "AUTHORITY");
        Authority requestStatus = authorityMapper.mapToModel(authorityDto);

        Mockito.when(authorityRepository.getReferenceById(authorityDto.getId()))
                .thenReturn(requestStatus);
        Mockito.lenient()
                .when(authorityRepository.save(Mockito.any(Authority.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(authorityDto, authorityService.update(authorityDto));
    }

    @Test
    public void updateNotExistedTest() {
        AuthorityDto authorityDto = new AuthorityDto(1L, "AUTHORITY");
        Mockito.when(authorityRepository.getReferenceById(authorityDto.getId()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> authorityService.update(authorityDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.when(authorityRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> authorityService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient()
                .doNothing()
                .when(authorityRepository).deleteById(Mockito.anyLong());

        Assertions.assertEquals(1, authorityService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .doThrow(DeleteStatementRepositoryException.class)
                .when(authorityRepository).deleteById(Mockito.anyLong());

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> authorityService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .doThrow(NullPointerException.class)
                .when(authorityRepository).deleteById(Mockito.nullable(Long.class));

        Assertions.assertThrows(NullPointerException.class,
                () -> authorityService.delete(null));
    }

    @Test
    public void selectTest() {
        Authority type = new Authority(1L, "AUTHORITY");
        Mockito.when(authorityRepository.getReferenceById(1L)).thenReturn(type);

        AuthorityDto authorityDto = authorityService.select(1L);
        Assertions.assertEquals(authorityDto, authorityMapper.mapToDto(type));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(authorityRepository.getReferenceById(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> authorityService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(authorityRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> authorityService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<Authority> requestStatuses = new ArrayList<>();
        requestStatuses.add(new Authority(1L,"AUTHORITY1"));
        requestStatuses.add(new Authority(2L,"AUTHORITY2"));

        Mockito.lenient().when(authorityRepository.findAll()).thenReturn(requestStatuses);

        List<AuthorityDto> requestStatusDtos = authorityService.selectAll();
        Assertions.assertEquals(2, requestStatusDtos.size());
        for (int i = 0; i < requestStatusDtos.size(); i++) {
            Assertions.assertEquals(requestStatusDtos.get(i), authorityMapper.mapToDto(requestStatuses.get(i)));
        }
    }
    
}
