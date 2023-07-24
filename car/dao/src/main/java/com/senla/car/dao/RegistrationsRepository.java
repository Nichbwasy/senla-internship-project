package com.senla.car.dao;

import com.senla.car.model.Registration;
import com.senla.common.dao.CrudRepository;

import java.util.List;

public interface RegistrationsRepository extends CrudRepository<Registration, Long> {

    /**
     * Selects all car registrations with the same name
     * @param modelName Registration name
     * @return List of all registrations with a same name
     */
    List<Registration> selectAllByModel(String modelName);

    /**
     * Selects all car registrations in specified max mass diapason
     * @param min Min max mass diapason value
     * @param max Max max mass diapason value
     * @return List of all car registration in selected max mass diapason
     */
    List<Registration> selectAllByAllowedMaxMassDiapason(Integer min, Integer max);

    /**
     * Selects all car registrations in specified unladen mass diapason
     * @param min Min unladen mass diapason value
     * @param max Max unladen mass diapason value
     * @return List of all car registration in selected unladen mass diapason
     */
    List<Registration> selectAllByUnladenMassDiapason(Integer min, Integer max);

    /**
     * Selects all car registrations where allowed max mass more than specified value
     * @param allowedMass Allowed max mas value
     * @return List of all car registration with max mass more than specified value
     */
    List<Registration> selectAllWithAllowedMaxMassMoreThen(Integer allowedMass);

    /**
     * Selects all car registration in specified year diapason
     * @param minYear Min year value
     * @param maxYear Max year value
     * @return List of all car registration in specified year diapason
     */
    List<Registration> selectAllByYearsDiapason(Integer minYear, Integer maxYear);

    /**
     * Selects all car registration with specified color
     * @param colorName Color name
     * @return List of all car registrations with specified color name
     */
    List<Registration> selectAllByColor(String colorName);

    /**
     * Selects all car registration with specified type
     * @param typeName Type name
     * @return List of all car registrations with specified type name
     */
    List<Registration> selectAllByType(String typeName);

}
