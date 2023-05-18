package com.senla.car.service;

import com.senla.car.dao.ColorsRepository;
import com.senla.car.dto.ColorDto;
import com.senla.car.model.Color;
import com.senla.car.service.impl.ColorsServiceImpl;
import com.senla.car.service.mappers.ColorsMapper;
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
public class ColorsServiceTests {

    @Spy
    private final ColorsMapper colorsMapper = Mappers.getMapper(ColorsMapper.class);

    @Mock
    private ColorsRepository colorsRepository;

    @InjectMocks
    private final ColorsServiceImpl colorsService = new ColorsServiceImpl();

    @Test
    public void insertTest() {
        ColorDto colorDto = new ColorDto(1L, "BLACK");
        Mockito.lenient()
                .when(colorsRepository.insert(Mockito.any(Color.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(colorDto, colorsService.insert(colorDto));
    }

    @Test
    public void insertNullTest() {
        Mockito.lenient()
                .when(colorsRepository.insert(Mockito.nullable(Color.class)))
                .thenThrow(InsertStatementRepositoryException.class);

        Assertions.assertThrows(InsertStatementRepositoryException.class,
                () -> colorsService.insert(null));
    }

    @Test
    public void updateTest() {
        ColorDto colorDto = new ColorDto(1L, "BLACK");
        Mockito.lenient()
                .when(colorsRepository.update(Mockito.any(Color.class)))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(colorDto, colorsService.update(colorDto));
    }

    @Test
    public void updateNotExistedTest() {
        ColorDto colorDto = new ColorDto(2L, "BLACK");
        Mockito.when(colorsRepository.update(Mockito.any(Color.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class, () -> colorsService.update(colorDto));
    }

    @Test
    public void updateNullTest() {
        Mockito.lenient()
                .when(colorsRepository.update(Mockito.nullable(Color.class)))
                .thenThrow(UpdateStatementRepositoryException.class);

        Assertions.assertThrows(UpdateStatementRepositoryException.class, () -> colorsService.update(null));
    }

    @Test
    public void deleteTest() {
        Mockito.lenient()
                .when(colorsRepository.delete(1L))
                .thenAnswer(a -> a.getArgument(0));

        Assertions.assertEquals(1, colorsService.delete(1L));
    }

    @Test
    public void deleteNotExistedTest() {
        Mockito.lenient()
                .when(colorsRepository.delete(Mockito.anyLong()))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> colorsService.delete(1L));
    }

    @Test
    public void deleteNullTest() {
        Mockito.lenient()
                .when(colorsRepository.delete(Mockito.nullable(Long.class)))
                .thenThrow(DeleteStatementRepositoryException.class);

        Assertions.assertThrows(DeleteStatementRepositoryException.class,
                () -> colorsService.delete(null));
    }

    @Test
    public void selectTest() {
        Color color = new Color(1L, "RED");
        Mockito.when(colorsRepository.select(1L)).thenReturn(color);

        ColorDto colorDto = colorsService.select(1L);
        Assertions.assertEquals(colorDto, colorsMapper.mapToDto(color));
    }

    @Test
    public void selectNotExistedTest() {
        Mockito.lenient()
                .when(colorsRepository.select(Mockito.anyLong()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> colorsService.select(1L));
    }

    @Test
    public void selectNullTest() {
        Mockito.lenient()
                .when(colorsRepository.select(Mockito.nullable(Long.class)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> colorsService.select(null));
    }

    @Test
    public void selectAllTest() {
        List<Color> colors = new ArrayList<>();
        colors.add(new Color(1L, "BLACK"));
        colors.add(new Color(2L, "WHITE"));

        Mockito.lenient().when(colorsRepository.selectAll()).thenReturn(colors);

        List<ColorDto> colorDtos = colorsService.selectAll();
        Assertions.assertEquals(2, colorDtos.size());
        for (int i = 0; i < colorDtos.size(); i++) {
            Assertions.assertEquals(colorDtos.get(i), colorsMapper.mapToDto(colors.get(i)));
        }
    }
    
}
