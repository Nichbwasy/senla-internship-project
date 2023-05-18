package com.senla.car.dao;

import com.senla.car.dao.configs.CarsDaoTestsConfiguration;
import com.senla.car.model.Car;
import com.senla.car.model.Condition;
import com.senla.car.model.Registration;
import com.senla.car.model.Status;
import com.senla.common.exception.repository.*;
import org.hibernate.QueryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;


@SpringJUnitConfig(CarsDaoTestsConfiguration.class)
public class CarsRepositoryTests {

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private StatusesRepository statusesRepository;

    @Autowired
    private ConditionsRepository conditionsRepository;

    @Autowired
    private RegistrationsRepository registrationsRepository;

    @Test
    public void selectTest() {
        Car car = new Car(null, "desc1", 100D, null, null , null);
        car = carsRepository.insert(car);

        Car result = carsRepository.select(car.getId());

        Assertions.assertEquals(car.getId(), result.getId());
    }

    @Test
    public void selectNotExistedTest() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> carsRepository.select(1000L));
    }

    @Test
    public void selectNullTest() {
        Assertions.assertThrows(SelectStatementRepositoryException.class, () -> carsRepository.select(null));
    }

    @Test
    public void selectAllTest() {
        Car car1= new Car(null, "desc1", 100D, null, null , null);
        Car car2 = new Car(null, "desc1", 100D, null, null , null);
        carsRepository.insert(car1);
        carsRepository.insert(car2);

        List<Car> result = carsRepository.selectAll();

        Assertions.assertTrue( () -> result.size() >= 2);
    }

    @Test
    public void insertTest() {
        Car car = new Car(null, "desc1", 100D, null, null , null);
        Car result = carsRepository.insert(car);

        Assertions.assertEquals(car, result);
    }

    @Test
    public void insertAlreadyExistedTest() {
        Car car = new Car(null, "desc1", 100D, null, null , null);
        carsRepository.insert(car);

        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> carsRepository.insert(car));
    }

    @Test
    public void insertNullTest() {
        Assertions.assertThrows(InsertStatementRepositoryException.class, () -> carsRepository.insert(null));
    }

    @Test
    public void updateTest() {
        Car car = new Car(null, "desc1", 100D, null, null , null);
        car = carsRepository.insert(car);
        Car newCar = new Car(car.getId(), "desc2", 200D, null, null , null);

        Car updated = carsRepository.update(newCar);
        Assertions.assertEquals(newCar, updated);
    }

    @Test
    public void updateNotExistedTest() {
        Car car = new Car(10011L, "desc1", 100D, null, null , null);

        Assertions.assertDoesNotThrow(() -> carsRepository.update(car));
    }

    @Test
    public void updateNullTest() {
        Assertions.assertThrows(UpdateStatementRepositoryException.class, () -> carsRepository.update(null));
    }

    @Test
    public void deleteTest() {
        Car car = new Car(null, "desc1", 100D, null, null , null);
        Long id = carsRepository.insert(car).getId();

        Assertions.assertEquals(id, carsRepository.delete(id));
    }

    @Test
    public void deleteNotExistedTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> carsRepository.delete(1000L));
    }

    @Test
    public void deleteNullTest() {
        Assertions.assertThrows(DeleteStatementRepositoryException.class, () -> carsRepository.delete(null));
    }

    @Test
    public void existByIdTest() {
        Car car = new Car(null, "desc1", 100D, null, null , null);
        Car result = carsRepository.insert(car);

        Assertions.assertTrue(() -> carsRepository.existById(result.getId()));
    }

    @Test
    public void existByIdWithNotExistedTest() {
        Assertions.assertFalse(() -> carsRepository.existById(2000L));
    }

    @Test
    public void existByIdNullTest() {
        Assertions.assertThrows(ExistStatementRepositoryException.class, () -> carsRepository.existById(null));
    }


    @Test
    public void selectAllByMileageDiapasonTest() {
        carsRepository.insert(new Car(null, "desc1", 100D, null, null , null));
        carsRepository.insert(new Car(null, "desc1", 150D, null, null , null));
        carsRepository.insert(new Car(null, "desc1", 200D, null, null , null));

        List<Car> cars = carsRepository.selectAllByMileageDiapason(120D, 160D);

        Assertions.assertEquals(1, cars.size());
    }

    @Test
    public void selectAllByMileageDiapasonWithNullParamTest() {
        Assertions.assertThrows(QueryException.class,
                () -> carsRepository.selectAllByMileageDiapason(null, null));
    }

    @Test
    public void selectAllByStatusNameTest() {
        Status st1 = statusesRepository.insert(new Status(null, "STATUS1"));
        Status st2 = statusesRepository.insert(new Status(null, "STATUS2"));
        carsRepository.insert(new Car(null, "desc1", 100D, null, null , st1));
        carsRepository.insert(new Car(null, "desc1", 100D, null, null , st2));
        carsRepository.insert(new Car(null, "desc1", 100D, null, null , st1));

        List<Car> cars = carsRepository.selectAllByStatusName("STATUS1");

        Assertions.assertEquals(2, cars.size());
    }

    @Test
    public void selectAllByStatusNameWithNullParamTest() {
        Assertions.assertThrows(QueryException.class, () ->  carsRepository.selectAllByStatusName(null));
    }

    @Test
    public void selectAllByConditionNameCriteriaTest() {
        Condition cond1 = conditionsRepository.insert(new Condition(null, "CONDITION1"));
        Condition cond2 = conditionsRepository.insert(new Condition(null, "CONDITION2"));
        carsRepository.insert(new Car(null, "desc1", 100D, null, cond1 , null));
        carsRepository.insert(new Car(null, "desc1", 100D, null, cond2 , null));
        carsRepository.insert(new Car(null, "desc1", 100D, null, cond1 , null));

        List<Car> cars = carsRepository.selectAllByConditionNameCriteria("CONDITION1");

        Assertions.assertEquals(2, cars.size());
    }

    @Test
    public void selectAllByNotExistedConditionNameCriteriaTest() {
        List<Car> cars = carsRepository.selectAllByConditionNameCriteria("CONDITION1000");

        Assertions.assertEquals(0, cars.size());
    }

    @Test
    public void selectAllByConditionNameCriteriaWithNullParamTest() {
        Assertions.assertThrows(QueryException.class, () -> carsRepository.selectAllByConditionNameCriteria(null));
    }

    @Test
    public void selectAllByConditionNameJPQLTest() {
        Condition cond1 = conditionsRepository.insert(new Condition(null, "CONDITION3"));
        Condition cond2 = conditionsRepository.insert(new Condition(null, "CONDITION4"));
        carsRepository.insert(new Car(null, "desc1", 100D, null, cond1 , null));
        carsRepository.insert(new Car(null, "desc1", 100D, null, cond2 , null));
        carsRepository.insert(new Car(null, "desc1", 100D, null, cond1 , null));

        List<Car> cars = carsRepository.selectAllByConditionNameJPQL("CONDITION3");

        Assertions.assertEquals(2, cars.size());
    }

    @Test
    public void selectAllByNotExistedConditionNameJPQLTest() {
        List<Car> cars = carsRepository.selectAllByConditionNameJPQL("CONDITION1000");

        Assertions.assertEquals(0, cars.size());
    }

    @Test
    public void selectAllByConditionNameJPQLWithNullParamTest() {
        List<Car> cars = carsRepository.selectAllByConditionNameJPQL(null);

        Assertions.assertEquals(0, cars.size());
    }

    @Test
    public void selectByIdEntityGraphWithoutFetchesTest() {
        Status st = statusesRepository.insert(new Status(null, "STATUS5"));
        Condition cond = conditionsRepository.insert(new Condition(null, "CONDITION5"));
        Registration reg = registrationsRepository
                .insert(new Registration(null, "crt_num", "crt_mod", 2001, "crt_bn", 1, 2, null, null));
        Car car = carsRepository.insert(new Car(null, "crt_desc1", 100D, reg, cond , st));

        Car result = carsRepository.selectByIdEntityGraphWithFetches(car.getId());

        Assertions.assertEquals(reg.getId(), result.getRegistration().getId());
        Assertions.assertEquals(st.getId(),  result.getStatus().getId());
        Assertions.assertEquals(cond.getId(), result.getCondition().getId());
    }

    @Test
    public void selectByIdEntityGraphWithoutFetchesAndNullParamTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> carsRepository.selectByIdEntityGraphWithoutFetches(null));
    }

    @Test
    public void selectByIdEntityGraphWithFetchesTest() {
        Status st = statusesRepository.insert(new Status(null, "STATUS6"));
        Condition cond = conditionsRepository.insert(new Condition(null, "CONDITION6"));
        Registration reg = registrationsRepository
                .insert(new Registration(null, "crt_num2", "crt_mod2", 2001, "crt_bn2", 1, 2, null, null));
        Car car = carsRepository.insert(new Car(null, "crt_desc1", 100D, reg, cond , st));

        Car result = carsRepository.selectByIdEntityGraphWithFetches(car.getId());

        Assertions.assertEquals(reg.getId(), result.getRegistration().getId());
        Assertions.assertEquals(st.getId(),  result.getStatus().getId());
        Assertions.assertEquals(cond.getId(), result.getCondition().getId());
    }

    @Test
    public void selectByIdEntityGraphWithFetchesAndNullParamTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> carsRepository.selectByIdEntityGraphWithFetches(null));
    }
}
