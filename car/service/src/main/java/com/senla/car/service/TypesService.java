package com.senla.car.service;

import com.senla.car.dto.TypeDto;

import java.util.List;

public interface TypesService {

    /**
     * Inserts a new type record
     * @param type Type dto
     * @return Inserted type record
     */
    TypeDto insert(TypeDto type);

    /**
     * Updates a type record with specified in dto id
     * @param type Type dto to update
     * @return Updated type record
     */
    TypeDto update(TypeDto type);

    /**
     * Selects type record by id
     * @param id Type id
     * @return Selected type with specified id
     */
    TypeDto select(Long id);

    /**
     * Removes type record with specified id
     * @param id Type id
     * @return Id of deleted type record
     */
    Long delete(Long id);

    /**
     * Selects all type records
     * @return List of all type records
     */
    List<TypeDto> selectAll();

    /**
     * Checks if type with specified name exists
     * @param name Type name
     * @return True if type with specified name exists, otherwise false
     */
    Boolean existsByName(String name);
}
