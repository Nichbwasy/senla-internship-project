package com.senla.car.dao;

import com.senla.car.dao.configs.CarsDaoTestsConfiguration;
import com.senla.car.model.Condition;
import com.senla.common.exception.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(CarsDaoTestsConfiguration.class)
public class ConditionsRepositoryTests {
    
    @Autowired
    private ConditionsRepository conditionsRepository;

    @Test
    public void selectTest() {
        Condition condition = new Condition(null, "name");
        condition = conditionsRepository.insert(condition);

        Condition result = conditionsRepository.select(condition.getId());

        Assertions.assertEquals(condition.getId(), result.getId());
    }

    @Test
    public void selectNotExistedTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> conditionsRepository.select(1000L));
    }

    @Test
    public void selectNullTest() {
        Assertions.assertThrows(SelectStatementRepositoryException.class, () -> conditionsRepository.select(null));
    }

    @Test
    public void selectAllTest() {
        conditionsRepository.insert(new Condition(null, "name2"));
        conditionsRepository.insert(new Condition(null, "name3"));

        List<Condition> result = conditionsRepository.selectAll();

        Assertions.assertTrue( () -> result.size() >= 2);
    }

    @Test
    public void insertTest() {
        Condition condition = new Condition(null, "name4");
        Condition result = conditionsRepository.insert(condition);

        Assertions.assertEquals(condition, result);
    }

    @Test
    public void insertAlreadyExistedTest() {
        Condition condition = new Condition(null, "name5");
        conditionsRepository.insert(condition);

        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> conditionsRepository.insert(condition));
    }

    @Test
    public void insertNullTest() {
        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> conditionsRepository.insert(null));
    }

    @Test
    public void updateTest() {
        Condition condition = new Condition(null, "name6");
        condition = conditionsRepository.insert(condition);
        Condition newColor = new Condition(condition.getId(), "name7");

        Condition updated = conditionsRepository.update(newColor);
        Assertions.assertEquals(newColor, updated);
    }

    @Test
    public void updateNotExistedTest() {
        Condition condition = new Condition(null, "name8");

        Assertions.assertDoesNotThrow(() -> conditionsRepository.update(condition));
    }

    @Test
    public void updateNullTest() {
        Assertions.assertThrows(UpdateStatementRepositoryException.class, () -> conditionsRepository.update(null));
    }

    @Test
    public void deleteTest() {
        Condition condition = new Condition(null, "name9");
        Long id = conditionsRepository.insert(condition).getId();

        Assertions.assertEquals(id, conditionsRepository.delete(id));
    }

    @Test
    public void deleteNotExistedTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> conditionsRepository.delete(1000L));
    }

    @Test
    public void deleteNullTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> conditionsRepository.delete(null));
    }

    @Test
    public void existByIdTest() {
        Condition condition = new Condition(null, "name10");
        Condition result = conditionsRepository.insert(condition);

        Assertions.assertTrue(() -> conditionsRepository.existById(result.getId()));
    }

    @Test
    public void existByIdWithNotExistedTest() {
        Assertions.assertFalse(() -> conditionsRepository.existById(2000L));
    }

    @Test
    public void existByIdNullTest() {
        Assertions.assertThrows(ExistStatementRepositoryException.class, () -> conditionsRepository.existById(null));
    }
    
}
