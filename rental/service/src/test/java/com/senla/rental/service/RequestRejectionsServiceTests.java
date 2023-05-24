package com.senla.rental.service;

import com.senla.common.exception.repository.EntityNotFoundException;
import com.senla.common.exception.repository.InsertStatementRepositoryException;
import com.senla.common.exception.repository.UpdateStatementRepositoryException;
import com.senla.rental.dao.RequestRejectionRepository;
import com.senla.rental.dto.RequestRejectionDto;
import com.senla.rental.model.RequestRejection;
import com.senla.rental.service.impl.RequestRejectionsServiceImpl;
import com.senla.rental.service.mappers.RequestRejectionMapper;
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
public class RequestRejectionsServiceTests {

    @InjectMocks
    private RequestRejectionsService requestRejectionsService = new RequestRejectionsServiceImpl();

    @Spy
    private RequestRejectionMapper requestRejectionMapper = Mappers.getMapper(RequestRejectionMapper.class);

    @Mock
    private RequestRejectionRepository requestRejectionsRepository;

    @Test
    public void insertTest() {
        RequestRejectionDto requestRejectionDto = new RequestRejectionDto(1L, "Title", "Text");
        Mockito.lenient()
                .when(requestRejectionsRepository.save(Mockito.any(RequestRejection.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(requestRejectionDto, requestRejectionsService.insert(requestRejectionDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(requestRejectionsRepository.save(Mockito.nullable(RequestRejection.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> requestRejectionsService.insert(null));
    }

    @Test
    public void updateTest() {
        RequestRejectionDto requestRejectionDto = new RequestRejectionDto(1L, "Title", "Text");
        RequestRejection rejection = requestRejectionMapper.mapToModel(requestRejectionDto);

        Mockito.when(requestRejectionsRepository.getReferenceById(requestRejectionDto.getId()))
                .thenReturn(rejection);
        Mockito.lenient()
                .when(requestRejectionsRepository.save(Mockito.any(RequestRejection.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(requestRejectionDto, requestRejectionsService.update(requestRejectionDto));
    }

    @Test
    public void updateNotExistedTest() {
        RequestRejectionDto requestRejectionDto = new RequestRejectionDto(2L, "Title2", "Text2");

        Mockito.when(requestRejectionsRepository.getReferenceById(Mockito.anyLong()))
                        .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> requestRejectionsService.update(requestRejectionDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.when(requestRejectionsRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> requestRejectionsService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient()
                .doNothing().when(requestRejectionsRepository).deleteById(1L);

        Assertions.assertEquals(1, requestRejectionsService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .doThrow(EntityNotFoundException.class)
                .when(requestRejectionsRepository).deleteById(Mockito.anyLong());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> requestRejectionsService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .doThrow(NullPointerException.class)
                .when(requestRejectionsRepository).deleteById(Mockito.nullable(Long.class));

        Assertions.assertThrows(NullPointerException.class,
                () -> requestRejectionsService.delete(null));
    }

    @Test
    public void selectTest() {
        RequestRejection requestRejection = new RequestRejection(1L, "Title", "Text");
        Mockito.when(requestRejectionsRepository.getReferenceById(1L)).thenReturn(requestRejection);

        RequestRejectionDto requestRejectionDto = requestRejectionsService.select(1L);
        Assertions.assertEquals(requestRejectionDto, requestRejectionMapper.mapToDto(requestRejection));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(requestRejectionsRepository.getReferenceById(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> requestRejectionsService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(requestRejectionsRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> requestRejectionsService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<RequestRejection> requestRejections = new ArrayList<>();
        requestRejections.add(new RequestRejection(1L, "Title1", "Text1"));
        requestRejections.add(new RequestRejection(1L, "Title2", "Text2"));

        Mockito.lenient().when(requestRejectionsRepository.findAll()).thenReturn(requestRejections);

        List<RequestRejectionDto> requestRejectionDtos = requestRejectionsService.selectAll();
        Assertions.assertEquals(2, requestRejectionDtos.size());
        for (int i = 0; i < requestRejectionDtos.size(); i++) {
            Assertions.assertEquals(requestRejectionDtos.get(i), requestRejectionMapper.mapToDto(requestRejections.get(i)));
        }
    }

}
