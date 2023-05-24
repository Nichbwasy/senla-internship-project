package com.senla.car.service;

import com.senla.car.dao.TypesRepository;
import com.senla.car.dto.TypeDto;
import com.senla.car.model.Type;
import com.senla.car.service.impl.TypesServiceImpl;
import com.senla.car.service.mappers.TypeMapper;
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
public class TypesServiceTests {

    @Spy
    private final TypeMapper typeMapper = Mappers.getMapper(TypeMapper.class);

    @Mock
    private TypesRepository typesRepository;

    @InjectMocks
    private final TypesServiceImpl typesService = new TypesServiceImpl();

    @Test
    public void insertTest() {
        TypeDto typeDto = new TypeDto(1L, "CAR");
        Mockito.lenient()
                .when(typesRepository.insert(Mockito.any(Type.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(typeDto, typesService.insert(typeDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(typesRepository.insert(Mockito.nullable(Type.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> typesService.insert(null));
    }

    @Test
    public void updateTest() {
        TypeDto typeDto = new TypeDto(1L, "CAR");
        Mockito.lenient()
                .when(typesRepository.update(Mockito.any(Type.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(typeDto, typesService.update(typeDto));
    }

    @Test
    public void updateNotExistedTest() {
        TypeDto typeDto = new TypeDto(2L, "TRUCK");
        Mockito.when(typesRepository.update(Mockito.any(Type.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> typesService.update(typeDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.lenient()
                .when(typesRepository.update(Mockito.nullable(Type.class)))
                .thenThrow(UpdateStatementRepositoryException.class);

        Assertions.assertThrows(UpdateStatementRepositoryException.class,
                () -> typesService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient().when(typesRepository.delete(1L)).thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(1, typesService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .when(typesRepository.delete(Mockito.anyLong()))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> typesService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .when(typesRepository.delete(Mockito.nullable(Long.class)))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> typesService.delete(null));
    }

    @Test
    public void selectTest() {
        Type type = new Type(1L, "CAR");
        Mockito.when(typesRepository.select(1L)).thenReturn(type);

        TypeDto typeDto = typesService.select(1L);
        Assertions.assertEquals(typeDto, typeMapper.mapToDto(type));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(typesRepository.select(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> typesService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(typesRepository.select(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> typesService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<Type> types = new ArrayList<>();
        types.add(new Type(1L, "CAR"));
        types.add(new Type(2L, "TRUCK"));

        Mockito.lenient().when(typesRepository.selectAll()).thenReturn(types);

        List<TypeDto> typeDtos = typesService.selectAll();
        Assertions.assertEquals(2, typeDtos.size());
        for (int i = 0; i < typeDtos.size(); i++) {
            Assertions.assertEquals(typeDtos.get(i), typeMapper.mapToDto(types.get(i)));
        }
    }


}
