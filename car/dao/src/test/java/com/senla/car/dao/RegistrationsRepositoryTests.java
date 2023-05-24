package com.senla.car.dao;

import com.senla.car.dao.configs.CarsDaoTestsConfiguration;
import com.senla.car.model.Color;
import com.senla.car.model.Registration;
import com.senla.car.model.Type;
import com.senla.common.exception.repository.*;
import org.hibernate.QueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(CarsDaoTestsConfiguration.class)
public class RegistrationsRepositoryTests {
    
    @Autowired
    private RegistrationsRepository registrationsRepository;

    @Autowired
    private ColorsRepository colorsRepository;

    @Autowired
    private TypesRepository typesRepository;

    @Test
    public void selectTest() {
        Registration registration = new Registration(null, "num1", "mod1", 1, "bn1", 100, 200, null, null);
        registration = registrationsRepository.insert(registration);

        Registration result = registrationsRepository.select(registration.getId());

        Assertions.assertEquals(registration.getId(), result.getId());
    }

    @Test
    public void selectNotExistedTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> registrationsRepository.select(1000L));
    }

    @Test
    public void selectNullTest() {
        Assertions.assertThrows(SelectStatementRepositoryException.class, () -> registrationsRepository.select(null));
    }

    @Test
    public void selectAllTest() {
        registrationsRepository.insert(new Registration(null, "num2", "mod2", 2, "bn2", 100, 200, null, null));
        registrationsRepository.insert(new Registration(null, "num3", "mod3", 3, "bn3", 100, 200, null, null));

        List<Registration> result = registrationsRepository.selectAll();

        Assertions.assertTrue( () -> result.size() >= 2);
    }

    @Test
    public void insertTest() {
        Registration registration = new Registration(null, "num4", "mod4", 4, "bn4", 100, 200, null, null);
        Registration result = registrationsRepository.insert(registration);

        Assertions.assertEquals(registration, result);
    }

    @Test
    public void insertAlreadyExistedTest() {
        Registration registration = new Registration(null, "num5", "mod5", 5, "bn5", 100, 200, null, null);
        registrationsRepository.insert(registration);

        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> registrationsRepository.insert(registration));
    }

    @Test
    public void insertNullTest() {
        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> registrationsRepository.insert(null));
    }

    @Test
    public void updateTest() {
        Registration registration = new Registration(null, "num6", "mod6", 6, "bn6", 100, 200, null, null);
        registration = registrationsRepository.insert(registration);
        Registration newColor = new Registration(registration.getId(), "num7", "mod7", 7, "bn7", 100, 200, null, null);

        Registration updated = registrationsRepository.update(newColor);
        Assertions.assertEquals(newColor, updated);
    }

    @Test
    public void updateNotExistedTest() {
        Registration registration = new Registration(null, "num8", "mod8", 8, "bn8", 100, 200, null, null);

        Assertions.assertDoesNotThrow(() -> registrationsRepository.update(registration));
    }

    @Test
    public void updateNullTest() {
        Assertions.assertThrows(UpdateStatementRepositoryException.class, () -> registrationsRepository.update(null));
    }

    @Test
    public void deleteTest() {
        Registration registration = new Registration(null, "num9", "mod9", 9, "bn9", 100, 200, null, null);
        Long id = registrationsRepository.insert(registration).getId();

        Assertions.assertEquals(id, registrationsRepository.delete(id));
    }

    @Test
    public void deleteNotExistedTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> registrationsRepository.delete(1000L));
    }

    @Test
    public void deleteNullTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> registrationsRepository.delete(null));
    }

    @Test
    public void existByIdTest() {
        Registration registration = new Registration(null, "num10", "mod10", 10, "bn10", 100, 200, null, null);
        Registration result = registrationsRepository.insert(registration);

        Assertions.assertTrue(() -> registrationsRepository.existById(result.getId()));
    }

    @Test
    public void existByIdWithNotExistedTest() {
        Assertions.assertFalse(() -> registrationsRepository.existById(2000L));
    }

    @Test
    public void existByIdNullTest() {
        Assertions.assertThrows(ExistStatementRepositoryException.class, () -> registrationsRepository.existById(null));
    }

    //||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

    @Test
    public void selectAllByModelTest() {
        registrationsRepository.insert(new Registration(null, "num11", "mod11", 11, "bn11", 100, 200, null, null));
        registrationsRepository.insert(new Registration(null, "num12", "mod11", 12, "bn12", 100, 200, null, null));

        List<Registration> registrations = registrationsRepository.selectAllByModel("mod11");

        Assertions.assertEquals(2, registrations.size());
    }

    @Test
    public void selectAllByModelWithNullParamTest() {
        List<Registration> registrations = registrationsRepository.selectAllByModel(null);

        Assertions.assertEquals(0, registrations.size());
    }

    @Test
    public void selectAllWithAllowedMaxMassMoreThenTest() {
        registrationsRepository.insert(new Registration(null, "num13", "mod13", 13, "bn13", 2000, 3000, null, null));
        registrationsRepository.insert(new Registration(null, "num14", "mod14", 14, "bn14", 2200, 3000, null, null));
        registrationsRepository.insert(new Registration(null, "num15", "mod15", 15, "bn15", 2400, 3000, null, null));

        List<Registration> registrations = registrationsRepository.selectAllWithAllowedMaxMassMoreThen(2000);

        Assertions.assertEquals(2, registrations.size());
        registrations.forEach(r -> Assertions.assertTrue(r.getAllowedMaxMass() > 2000));
    }

    @Test
    public void selectAllWithAllowedMaxMassMoreThenWithNullParamTest() {
        Assertions.assertThrows(QueryException.class,
                () -> registrationsRepository.selectAllWithAllowedMaxMassMoreThen(null));
    }

    @Test
    public void selectAllByYearsTest() {
        registrationsRepository.insert(new Registration(null, "num16", "mod16", 16, "bn16", 300, 400, null, null));
        registrationsRepository.insert(new Registration(null, "num17", "mod17", 17, "bn17", 300, 400, null, null));
        registrationsRepository.insert(new Registration(null, "num18", "mod18", 18, "bn18", 300, 400, null, null));
        registrationsRepository.insert(new Registration(null, "num19", "mod19", 19, "bn19", 300, 400, null, null));

        List<Registration> registrations = registrationsRepository.selectAllByYearsDiapason(17, 18);

        Assertions.assertEquals(2, registrations.size());
        registrations.forEach(r -> Assertions.assertTrue((17 <= r.getReleaseYear()) && (r.getReleaseYear() <= 18)));
    }

    @Test
    public void selectAllByYearsWithNullParamsTest() {
        Assertions.assertThrows(QueryException.class,
                () -> registrationsRepository.selectAllByYearsDiapason(null, null));
    }

    @Test
    public void selectAllByAllowedMaxMassDiapasonTest() {
        registrationsRepository.insert(new Registration(null, "num20", "mod20", 20, "bn20", 400, 500, null, null));
        registrationsRepository.insert(new Registration(null, "num21", "mod21", 21, "bn21", 420, 500, null, null));
        registrationsRepository.insert(new Registration(null, "num22", "mod22", 22, "bn22", 440, 500, null, null));

        List<Registration> registrations = registrationsRepository.selectAllByAllowedMaxMassDiapason(410, 430);

        Assertions.assertEquals(1, registrations.size());
        registrations.forEach(r -> Assertions.assertTrue((410 <= r.getAllowedMaxMass()) && (r.getAllowedMaxMass() <= 430)));
    }

    @Test
    public void selectAllByAllowedMaxMassDiapasonWithNullParamsTest() {
        Assertions.assertThrows(QueryException.class,
                () -> registrationsRepository.selectAllByAllowedMaxMassDiapason(null, null));
    }

    @Test
    public void selectAllByAllowedMaxMassDiapasonWithIncorrectDiapasonParamsTest() {
        List<Registration> registrations = registrationsRepository.selectAllByAllowedMaxMassDiapason(20000, 14000);

        Assertions.assertEquals(0, registrations.size());
    }

    @Test
    public void selectAllByUnladenMassDiapasonTest() {
        registrationsRepository.insert(new Registration(null, "num23", "mod23", 23, "bn23", 500, 600, null, null));
        registrationsRepository.insert(new Registration(null, "num24", "mod24", 24, "bn24", 500, 620, null, null));
        registrationsRepository.insert(new Registration(null, "num25", "mod25", 25, "bn25", 500, 640, null, null));

        List<Registration> registrations = registrationsRepository.selectAllByUnladenMassDiapason(610, 630);

        Assertions.assertEquals(1, registrations.size());
        registrations.forEach(r -> Assertions.assertTrue((610 <= r.getUnladenMass()) && (r.getUnladenMass() <= 630)));
    }

    @Test
    public void selectAllByUnladenMassDiapasonWithNullParamsTest() {
        Assertions.assertThrows(QueryException.class,
                () -> registrationsRepository.selectAllByUnladenMassDiapason(null, null));
    }

    @Test
    public void selectAllByUnladenMassDiapasonWithIncorrectDiapasonParamsTest() {
        List<Registration> registrations = registrationsRepository.selectAllByUnladenMassDiapason(15000, 11000);

        Assertions.assertEquals(0, registrations.size());
    }

    @Test
    public void selectAllByColorTest() {
        Color color = colorsRepository.insert(new Color(null, "selectAllByColorTest1"));
        registrationsRepository.insert(new Registration(null, "num26", "mod26", 26, "bn26", 700, 800, color, null));

        List<Registration> registrations = registrationsRepository.selectAllByColor("selectAllByColorTest1");

        Assertions.assertEquals(1, registrations.size());
    }

    @Test
    public void selectAllByNotExistedColorTest() {
        List<Registration> registrations = registrationsRepository.selectAllByColor("NOT_EXISTED");

        Assertions.assertEquals(0, registrations.size());
    }

    @Test
    public void selectAllByColorWithNullParamsTest() {
        Assertions.assertThrows(QueryException.class,
                () -> registrationsRepository.selectAllByColor(null));
    }

    @Test
    public void selectAllByTypeTest() {
        Type type = typesRepository.insert(new Type(null, "selectAllByTypeTest"));
        registrationsRepository.insert(new Registration(null, "num27", "mod27", 27, "bn27", 700, 800, null, type));

        List<Registration> registrations = registrationsRepository.selectAllByType("selectAllByTypeTest");

        Assertions.assertEquals(1, registrations.size());
    }

    @Test
    public void selectAllByNotExistedTypeTest() {
        List<Registration> registrations = registrationsRepository.selectAllByType("NOT_EXISTED");

        Assertions.assertEquals(0, registrations.size());
    }

    @Test
    public void selectAllByTypeWithNullParamsTest() {
        Assertions.assertThrows(QueryException.class,
                () -> registrationsRepository.selectAllByType(null));
    }

}
