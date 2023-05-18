package com.senla.car.dao;

import com.senla.car.dao.configs.CarsDaoTestsConfiguration;
import com.senla.car.model.Status;
import com.senla.common.exception.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(CarsDaoTestsConfiguration.class)
public class StatusesRepositoryTests {
    
    @Autowired
    private StatusesRepository statusesRepository;

    @Test
    public void selectTest() {
        Status status = new Status(null, "name");
        status = statusesRepository.insert(status);

        Status result = statusesRepository.select(status.getId());

        Assertions.assertEquals(status.getId(), result.getId());
    }

    @Test
    public void selectNotExistedTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> statusesRepository.select(1000L));
    }

    @Test
    public void selectNullTest() {
        Assertions.assertThrows(SelectStatementRepositoryException.class, () -> statusesRepository.select(null));
    }

    @Test
    public void selectAllTest() {
        statusesRepository.insert(new Status(null, "name2"));
        statusesRepository.insert(new Status(null, "name3"));

        List<Status> result = statusesRepository.selectAll();

        Assertions.assertTrue( () -> result.size() >= 2);
    }

    @Test
    public void insertTest() {
        Status status = new Status(null, "name4");
        Status result = statusesRepository.insert(status);

        Assertions.assertEquals(status, result);
    }

    @Test
    public void insertAlreadyExistedTest() {
        Status status = new Status(null, "name5");
        statusesRepository.insert(status);

        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> statusesRepository.insert(status));
    }

    @Test
    public void insertNullTest() {
        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> statusesRepository.insert(null));
    }

    @Test
    public void updateTest() {
        Status status = new Status(null, "name6");
        status = statusesRepository.insert(status);
        Status newColor = new Status(status.getId(), "name7");

        Status updated = statusesRepository.update(newColor);
        Assertions.assertEquals(newColor, updated);
    }

    @Test
    public void updateNotExistedTest() {
        Status status = new Status(null, "name8");

        Assertions.assertDoesNotThrow(() -> statusesRepository.update(status));
    }

    @Test
    public void updateNullTest() {
        Assertions.assertThrows(UpdateStatementRepositoryException.class, () -> statusesRepository.update(null));
    }

    @Test
    public void deleteTest() {
        Status status = new Status(null, "name9");
        Long id = statusesRepository.insert(status).getId();

        Assertions.assertEquals(id, statusesRepository.delete(id));
    }

    @Test
    public void deleteNotExistedTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> statusesRepository.delete(1000L));
    }

    @Test
    public void deleteNullTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> statusesRepository.delete(null));
    }

    @Test
    public void existByIdTest() {
        Status status = new Status(null, "name10");
        Status result = statusesRepository.insert(status);

        Assertions.assertTrue(() -> statusesRepository.existById(result.getId()));
    }

    @Test
    public void existByIdWithNotExistedTest() {
        Assertions.assertFalse(() -> statusesRepository.existById(2000L));
    }

    @Test
    public void existByIdNullTest() {
        Assertions.assertThrows(ExistStatementRepositoryException.class, () -> statusesRepository.existById(null));
    }


}
