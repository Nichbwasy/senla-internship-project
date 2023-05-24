package com.senla.car.service;

import com.senla.car.dao.ConditionsRepository;
import com.senla.car.dto.ConditionDto;
import com.senla.car.model.Condition;
import com.senla.car.service.impl.ConditionsServiceImpl;
import com.senla.car.service.mappers.ConditionMapper;
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
public class ConditionsServiceTests {

    @Spy
    private final ConditionMapper conditionMapper = Mappers.getMapper(ConditionMapper.class);

    @Mock
    private ConditionsRepository conditionsRepository;

    @InjectMocks
    private final ConditionsServiceImpl conditionsService = new ConditionsServiceImpl();

    @Test
    public void insertTest() {
        ConditionDto conditionDto = new ConditionDto(1L, "NEW");
        Mockito.lenient()
                .when(conditionsRepository.insert(Mockito.any(Condition.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(conditionDto, conditionsService.insert(conditionDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(conditionsRepository.insert(Mockito.nullable(Condition.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> conditionsService.insert(null));
    }

    @Test
    public void updateTest() {
        ConditionDto conditionDto = new ConditionDto(1L, "NEW");
        Mockito.lenient()
                .when(conditionsRepository.update(Mockito.any(Condition.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(conditionDto, conditionsService.update(conditionDto));
    }

    @Test
    public void updateNotExistedTest() {
        ConditionDto conditionDto = new ConditionDto(2L, "OLD");
        Mockito.when(conditionsRepository.update(Mockito.any(Condition.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> conditionsService.update(conditionDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.lenient()
                .when(conditionsRepository.update(Mockito.nullable(Condition.class)))
                .thenThrow(UpdateStatementRepositoryException.class);

        Assertions.assertThrows(UpdateStatementRepositoryException.class, () -> conditionsService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient().when(conditionsRepository.delete(1L)).thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(1, conditionsService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .when(conditionsRepository.delete(Mockito.anyLong()))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> conditionsService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .when(conditionsRepository.delete(Mockito.nullable(Long.class)))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> conditionsService.delete(null));
    }

    @Test
    public void selectTest() {
        Condition condition = new Condition(1L, "OLD");
        Mockito.when(conditionsRepository.select(1L)).thenReturn(condition);

        ConditionDto conditionDto = conditionsService.select(1L);
        Assertions.assertEquals(conditionDto, conditionMapper.mapToDto(condition));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(conditionsRepository.select(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> conditionsService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(conditionsRepository.select(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> conditionsService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<Condition> conditions = new ArrayList<>();
        conditions.add(new Condition(1L, "NEW"));
        conditions.add(new Condition(2L, "OLD"));

        Mockito.lenient().when(conditionsRepository.selectAll()).thenReturn(conditions);

        List<ConditionDto> conditionDtos = conditionsService.selectAll();
        Assertions.assertEquals(2, conditionDtos.size());
        for (int i = 0; i < conditionDtos.size(); i++) {
            Assertions.assertEquals(conditionDtos.get(i), conditionMapper.mapToDto(conditions.get(i)));
        }
    }

}
