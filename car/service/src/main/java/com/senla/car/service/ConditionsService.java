package com.senla.car.service;

import com.senla.car.dto.ConditionDto;

import java.util.List;

public interface ConditionsService {
    /**
     * Inserts a new condition record
     * @param conditionDto Condition dto
     * @return Inserted condition record
     */
    ConditionDto insert(ConditionDto conditionDto);

    /**
     * Updates a condition record with specified in dto id
     * @param conditionDto Condition dto to update
     * @return Updated condition record
     */
    ConditionDto update(ConditionDto conditionDto);

    /**
     * Selects condition record by id
     * @param id Condition id
     * @return Selected condition with specified id
     */
    ConditionDto select(Long id);

    /**
     * Removes condition record with specified id
     * @param id Condition id
     * @return Id of deleted car record
     */
    Long delete(Long id);

    /**
     * Selects all condition records
     * @return List of all condition records
     */
    List<ConditionDto> selectAll();

    /**
     * Checks if condition with specified name exists
     * @param name Condition name
     * @return True if condition with specified name exists, otherwise false
     */
    Boolean existsByName(String name);
}
