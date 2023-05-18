package com.senla.car.dao;

import com.senla.car.dao.configs.CarsDaoTestsConfiguration;
import com.senla.car.model.Type;
import com.senla.common.exception.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(CarsDaoTestsConfiguration.class)
public class TypesRepositoryTests {
    
    @Autowired
    private TypesRepository typesRepository;

    @Test
    public void selectTest() {
        Type type = new Type(null, "name");
        type = typesRepository.insert(type);

        Type result = typesRepository.select(type.getId());

        Assertions.assertEquals(type.getId(), result.getId());
    }

    @Test
    public void selectNotExistedTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> typesRepository.select(1000L));
    }

    @Test
    public void selectNullTest() {
        Assertions.assertThrows(SelectStatementRepositoryException.class, () -> typesRepository.select(null));
    }

    @Test
    public void selectAllTest() {
        typesRepository.insert(new Type(null, "name2"));
        typesRepository.insert(new Type(null, "name3"));

        List<Type> result = typesRepository.selectAll();

        Assertions.assertTrue( () -> result.size() >= 2);
    }

    @Test
    public void insertTest() {
        Type type = new Type(null, "name4");
        Type result = typesRepository.insert(type);

        Assertions.assertEquals(type, result);
    }

    @Test
    public void insertAlreadyExistedTest() {
        Type type = new Type(null, "name5");
        typesRepository.insert(type);

        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> typesRepository.insert(type));
    }

    @Test
    public void insertNullTest() {
        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> typesRepository.insert(null));
    }

    @Test
    public void updateTest() {
        Type type = new Type(null, "name6");
        type = typesRepository.insert(type);
        Type newColor = new Type(type.getId(), "name7");

        Type updated = typesRepository.update(newColor);
        Assertions.assertEquals(newColor, updated);
    }

    @Test
    public void updateNotExistedTest() {
        Type type = new Type(null, "name8");

        Assertions.assertDoesNotThrow(() -> typesRepository.update(type));
    }

    @Test
    public void updateNullTest() {
        Assertions.assertThrows(UpdateStatementRepositoryException.class, () -> typesRepository.update(null));
    }

    @Test
    public void deleteTest() {
        Type type = new Type(null, "name9");
        Long id = typesRepository.insert(type).getId();

        Assertions.assertEquals(id, typesRepository.delete(id));
    }

    @Test
    public void deleteNotExistedTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> typesRepository.delete(1000L));
    }

    @Test
    public void deleteNullTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> typesRepository.delete(null));
    }

    @Test
    public void existByIdTest() {
        Type type = new Type(null, "name10");
        Type result = typesRepository.insert(type);

        Assertions.assertTrue(() -> typesRepository.existById(result.getId()));
    }

    @Test
    public void existByIdWithNotExistedTest() {
        Assertions.assertFalse(() -> typesRepository.existById(2000L));
    }

    @Test
    public void existByIdNullTest() {
        Assertions.assertThrows(ExistStatementRepositoryException.class, () -> typesRepository.existById(null));
    }


}
