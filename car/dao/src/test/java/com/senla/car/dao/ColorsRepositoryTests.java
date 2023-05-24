package com.senla.car.dao;

import com.senla.car.dao.configs.CarsDaoTestsConfiguration;
import com.senla.car.model.Color;
import com.senla.common.exception.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(CarsDaoTestsConfiguration.class)
public class ColorsRepositoryTests {

    @Autowired
    private ColorsRepository colorsRepository;

    @Test
    public void selectTest() {
        Color color = new Color(null, "name");
        color = colorsRepository.insert(color);

        Color result = colorsRepository.select(color.getId());

        Assertions.assertEquals(color.getId(), result.getId());
    }

    @Test
    public void selectNotExistedTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> colorsRepository.select(1000L));
    }

    @Test
    public void selectNullTest() {
        Assertions.assertThrows(SelectStatementRepositoryException.class, () -> colorsRepository.select(null));
    }

    @Test
    public void selectAllTest() {
        colorsRepository.insert(new Color(null, "name2"));
        colorsRepository.insert(new Color(null, "name3"));

        List<Color> result = colorsRepository.selectAll();

        Assertions.assertTrue( () -> result.size() >= 2);
    }

    @Test
    public void insertTest() {
        Color color = new Color(null, "name4");
        Color result = colorsRepository.insert(color);

        Assertions.assertEquals(color, result);
    }

    @Test
    public void insertAlreadyExistedTest() {
        Color color = new Color(null, "name5");
        colorsRepository.insert(color);

        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> colorsRepository.insert(color));
    }

    @Test
    public void insertNullTest() {
        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> colorsRepository.insert(null));
    }

    @Test
    public void updateTest() {
        Color color = new Color(null, "name6");
        color = colorsRepository.insert(color);
        Color newColor = new Color(color.getId(), "name7");

        Color updated = colorsRepository.update(newColor);
        Assertions.assertEquals(newColor, updated);
    }

    @Test
    public void updateNotExistedTest() {
        Color color = new Color(null, "name8");

        Assertions.assertDoesNotThrow(() -> colorsRepository.update(color));
    }

    @Test
    public void updateNullTest() {
        Assertions.assertThrows(UpdateStatementRepositoryException.class, () -> colorsRepository.update(null));
    }

    @Test
    public void deleteTest() {
        Color color = new Color(null, "name9");
        Long id = colorsRepository.insert(color).getId();

        Assertions.assertEquals(id, colorsRepository.delete(id));
    }

    @Test
    public void deleteNotExistedTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> colorsRepository.delete(1000L));
    }

    @Test
    public void deleteNullTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> colorsRepository.delete(null));
    }

    @Test
    public void existByIdTest() {
        Color color = new Color(null, "name10");
        Color result = colorsRepository.insert(color);

        Assertions.assertTrue(() -> colorsRepository.existById(result.getId()));
    }

    @Test
    public void existByIdWithNotExistedTest() {
        Assertions.assertFalse(() -> colorsRepository.existById(2000L));
    }

    @Test
    public void existByIdNullTest() {
        Assertions.assertThrows(ExistStatementRepositoryException.class, () -> colorsRepository.existById(null));
    }


}
