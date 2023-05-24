package com.senla.rental.service;

import com.senla.common.exception.repository.DeleteStatementRepositoryException;
import com.senla.common.exception.repository.EntityNotFoundException;
import com.senla.common.exception.repository.InsertStatementRepositoryException;
import com.senla.rental.dao.RefundCompensationRepository;
import com.senla.rental.dto.RefundCompensationDto;
import com.senla.rental.model.RefundCompensation;
import com.senla.rental.service.impl.RefundCompensationsServiceImpl;
import com.senla.rental.service.mappers.RefundCompensationMapper;
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
public class RefundCompensationsServiceTests {

    @InjectMocks
    private RefundCompensationsService refundCompensationsService = new RefundCompensationsServiceImpl();
    @Spy
    private RefundCompensationMapper refundCompensationMapper = Mappers.getMapper(RefundCompensationMapper.class);
    @Mock
    private RefundCompensationRepository refundCompensationsRepository;


    @Test
    public void insertTest() {
        RefundCompensationDto refundCompensationDto =
                new RefundCompensationDto(1L, "Title", "Text", false, 200D);
        
        Mockito.lenient()
                .when(refundCompensationsRepository.save(Mockito.any(RefundCompensation.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(refundCompensationDto, refundCompensationsService.insert(refundCompensationDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(refundCompensationsRepository.save(Mockito.nullable(RefundCompensation.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> refundCompensationsService.insert(null));
    }

    @Test
    public void updateTest() {
        RefundCompensationDto refundCompensationDto =
                new RefundCompensationDto(1L, "Title", "Text", false, 200D);
        RefundCompensation refundCompensation = refundCompensationMapper.mapToModel(refundCompensationDto);

        Mockito.when(refundCompensationsRepository.getReferenceById(refundCompensationDto.getId()))
                .thenReturn(refundCompensation);
        Mockito.lenient()
                .when(refundCompensationsRepository.save(Mockito.any(RefundCompensation.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(refundCompensationDto, refundCompensationsService.update(refundCompensationDto));
    }

    @Test
    public void updateNotExistedTest() {
        RefundCompensationDto refundCompensationDto =
                new RefundCompensationDto(2L, "Title2", "Text2", false, 400D);

        Mockito.when(refundCompensationsRepository.getReferenceById(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> refundCompensationsService.update(refundCompensationDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.when(refundCompensationsRepository.getReferenceById(Mockito.nullable(Long.class)))
                        .thenThrow(NullPointerException.class);

        Assertions.assertThrows(NullPointerException.class,
                () -> refundCompensationsService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient().doNothing().when(refundCompensationsRepository).deleteById(1L);

        Assertions.assertEquals(1, refundCompensationsService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .doThrow(DeleteStatementRepositoryException.class)
                .when(refundCompensationsRepository).deleteById(Mockito.anyLong());

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> refundCompensationsService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .doThrow(DeleteStatementRepositoryException.class)
                .when(refundCompensationsRepository).deleteById(Mockito.nullable(Long.class));

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> refundCompensationsService.delete(null));
    }

    @Test
    public void selectTest() {
        RefundCompensation refundCompensation =
                new RefundCompensation(1L, "Title", "Text", false, 200D);

        Mockito.when(refundCompensationsRepository.getReferenceById(1L)).thenReturn(refundCompensation);

        RefundCompensationDto refundCompensationDto = refundCompensationsService.select(1L);
        Assertions.assertEquals(refundCompensationDto, refundCompensationMapper.mapToDto(refundCompensation));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(refundCompensationsRepository.getReferenceById(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> refundCompensationsService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(refundCompensationsRepository.getReferenceById(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> refundCompensationsService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<RefundCompensation> refundCompensations = new ArrayList<>();
        refundCompensations.add(new RefundCompensation(1L, "Title", "Text", false, 200D));
        refundCompensations.add(new RefundCompensation(2L, "Title2", "Text2", true, 400D));

        Mockito.lenient().when(refundCompensationsRepository.findAll()).thenReturn(refundCompensations);

        List<RefundCompensationDto> refundCompensationsDtos = refundCompensationsService.selectAll();
        Assertions.assertEquals(2, refundCompensationsDtos.size());
        for (int i = 0; i < refundCompensationsDtos.size(); i++) {
            Assertions.assertEquals(refundCompensationsDtos.get(i), refundCompensationMapper.mapToDto(refundCompensations.get(i)));
        }
    }

}
