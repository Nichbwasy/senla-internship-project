package com.senla.car.dao;

import com.senla.car.model.Registration;
import com.senla.common.dao.CrudRepository;

import java.util.List;

public interface RegistrationsRepository extends CrudRepository<Registration, Long> {

    List<Registration> selectAllByModel(String modelName);

    List<Registration> selectAllByAllowedMaxMassDiapason(Integer min, Integer max);

    List<Registration> selectAllByUnladenMassDiapason(Integer min, Integer max);

    List<Registration> selectAllWithAllowedMaxMassMoreThen(Integer allowedMass);

    List<Registration> selectAllByYearsDiapason(Integer minYear, Integer maxYear);

    List<Registration> selectAllByColor(String colorName);

    List<Registration> selectAllByType(String typeName);

}
