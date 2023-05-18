package com.senla.car.service;

import com.senla.car.dao.StatusesRepository;
import com.senla.car.dto.StatusDto;
import com.senla.car.model.Status;
import com.senla.car.service.impl.StatusesServiceImpl;
import com.senla.car.service.mappers.StatusMapper;
import com.senla.common.exception.repository.DeleteStatementRepositoryException;
import com.senla.common.exception.repository.EntityNotFoundException;
import com.senla.common.exception.repository.InsertStatementRepositoryException;
import com.senla.common.exception.repository.UpdateStatementRepositoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class StatusesServiceTests {

    @Spy
    private final StatusMapper statusMapper = Mappers.getMapper(StatusMapper.class);

    @Mock
    private StatusesRepository statusesRepository;

    @InjectMocks
    private final StatusesServiceImpl statusesService = new StatusesServiceImpl();

    @Test
    public void insertTest() {
        StatusDto statusDto = new StatusDto(1L, "AVAILABLE");
        Mockito.lenient()
                .when(statusesRepository.insert(Mockito.any(Status.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(statusDto, statusesService.insert(statusDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(statusesRepository.insert(Mockito.nullable(Status.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> statusesService.insert(null));
    }

    @Test
    public void updateTest() {
        StatusDto statusDto = new StatusDto(1L, "AVAILABLE");
        Mockito.lenient()
                .when(statusesRepository.update(Mockito.any(Status.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(statusDto, statusesService.update(statusDto));
    }

    @Test
    public void updateNotExistedTest() {
        StatusDto statusDto = new StatusDto(2L, "NOT_AVAILABLE");
        Mockito.when(statusesRepository.update(Mockito.any(Status.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> statusesService.update(statusDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.lenient()
                .when(statusesRepository.update(Mockito.nullable(Status.class)))
                .thenThrow(UpdateStatementRepositoryException.class);

        Assertions.assertThrows(UpdateStatementRepositoryException.class,
                () -> statusesService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient().when(statusesRepository.delete(1L)).thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(1, statusesService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .when(statusesRepository.delete(Mockito.anyLong()))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> statusesService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .when(statusesRepository.delete(Mockito.nullable(Long.class)))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> statusesService.delete(null));
    }

    @Test
    public void selectTest() {
        Status status = new Status(1L, "AVAILABLE");
        Mockito.when(statusesRepository.select(1L)).thenReturn(status);

        StatusDto statusDto = statusesService.select(1L);
        Assertions.assertEquals(statusDto, statusMapper.mapToDto(status));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(statusesRepository.select(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> statusesService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(statusesRepository.select(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> statusesService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<Status> statuses = new ArrayList<>();
        statuses.add(new Status(1L, "AVAILABLE"));
        statuses.add(new Status(2L, "NOT_AVAILABLE"));

        Mockito.lenient().when(statusesRepository.selectAll()).thenReturn(statuses);

        List<StatusDto> statusDtos = statusesService.selectAll();
        Assertions.assertEquals(2, statusDtos.size());
        for (int i = 0; i < statusDtos.size(); i++) {
            Assertions.assertEquals(statusDtos.get(i), statusMapper.mapToDto(statuses.get(i)));
        }
    }

}
