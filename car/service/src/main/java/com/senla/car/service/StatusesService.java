package com.senla.car.service;

import com.senla.car.dto.StatusDto;

import java.util.List;

public interface StatusesService {

    /**
     * Inserts a new status record
     * @param statusDto Car dto
     * @return Inserted status record
     */
    StatusDto insert(StatusDto statusDto);

    /**
     * Updates a status record with specified in dto id
     * @param statusDto Status dto to update
     * @return Updated status record
     */
    StatusDto update(StatusDto statusDto);

    /**
     * Selects status record by id
     * @param id Status id
     * @return Selected status with specified id
     */
    StatusDto select(Long id);

    /**
     * Removes status record with specified id
     * @param id Status id
     * @return Id of deleted status record
     */
    Long delete(Long id);

    /**
     * Selects all status records
     * @return List of all status records
     */
    List<StatusDto> selectAll();

    /**
     * Checks if status with specified name exists
     * @param name Status name
     * @return True if status with specified name exists, otherwise false
     */
    Boolean existsByName(String name);
}
