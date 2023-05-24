package com.senla.rental.service;

import com.senla.common.exception.repository.DeleteStatementRepositoryException;
import com.senla.common.exception.repository.EntityNotFoundException;
import com.senla.common.exception.repository.InsertStatementRepositoryException;
import com.senla.rental.dao.RequestStatusRepository;
import com.senla.rental.dto.RequestStatusDto;
import com.senla.rental.model.RequestStatus;
import com.senla.rental.service.impl.RequestStatusesServiceImpl;
import com.senla.rental.service.mappers.RequestStatusMapper;
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
public class RequestStatusesServiceTests {

    @InjectMocks
    private RequestStatusesService requestStatusesService = new RequestStatusesServiceImpl();
    @Spy
    private RequestStatusMapper requestStatusMapper = Mappers.getMapper(RequestStatusMapper.class);

    @Mock
    private RequestStatusRepository requestStatusesRepository;

    @Test
    public void insertTest() {
        RequestStatusDto requestStatusDto = new RequestStatusDto(1L, "CREATED");
        Mockito.lenient()
                .when(requestStatusesRepository.save(Mockito.any(RequestStatus.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(requestStatusDto, requestStatusesService.insert(requestStatusDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(requestStatusesRepository.save(Mockito.nullable(RequestStatus.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> requestStatusesService.insert(null));
    }

    @Test
    public void updateTest() {
        RequestStatusDto requestStatusDto = new RequestStatusDto(1L, "CREATED");
        RequestStatus requestStatus = requestStatusMapper.mapToModel(requestStatusDto);

        Mockito.when(requestStatusesRepository.getReferenceById(requestStatusDto.getId()))
                .thenReturn(requestStatus);
        Mockito.lenient()
                .when(requestStatusesRepository.save(Mockito.any(RequestStatus.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(requestStatusDto, requestStatusesService.update(requestStatusDto));
    }

    @Test
    public void updateNotExistedTest() {
        RequestStatusDto requestStatusDto = new RequestStatusDto(1L, "CREATED");
        Mockito.when(requestStatusesRepository.getReferenceById(requestStatusDto.getId()))
                        .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> requestStatusesService.update(requestStatusDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.when(requestStatusesRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> requestStatusesService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient()
                .doNothing()
                .when(requestStatusesRepository).deleteById(Mockito.anyLong());

        Assertions.assertEquals(1, requestStatusesService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .doThrow(DeleteStatementRepositoryException.class)
                .when(requestStatusesRepository).deleteById(Mockito.anyLong());

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> requestStatusesService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .doThrow(NullPointerException.class)
                .when(requestStatusesRepository).deleteById(Mockito.nullable(Long.class));

        Assertions.assertThrows(NullPointerException.class,
                () -> requestStatusesService.delete(null));
    }

    @Test
    public void selectTest() {
        RequestStatus type = new RequestStatus(1L, "CREATED");
        Mockito.when(requestStatusesRepository.getReferenceById(1L)).thenReturn(type);

        RequestStatusDto requestStatusDto = requestStatusesService.select(1L);
        Assertions.assertEquals(requestStatusDto, requestStatusMapper.mapToDto(type));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(requestStatusesRepository.getReferenceById(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> requestStatusesService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(requestStatusesRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> requestStatusesService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<RequestStatus> requestStatuses = new ArrayList<>();
        requestStatuses.add(new RequestStatus(1L,"CREATED"));
        requestStatuses.add(new RequestStatus(2L,"ACCEPTED"));

        Mockito.lenient().when(requestStatusesRepository.findAll()).thenReturn(requestStatuses);

        List<RequestStatusDto> requestStatusDtos = requestStatusesService.selectAll();
        Assertions.assertEquals(2, requestStatusDtos.size());
        for (int i = 0; i < requestStatusDtos.size(); i++) {
            Assertions.assertEquals(requestStatusDtos.get(i), requestStatusMapper.mapToDto(requestStatuses.get(i)));
        }
    }
    
}
