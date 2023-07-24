package com.senla.car.service;

import com.senla.car.dto.RegistrationDto;

import java.util.List;

public interface RegistrationsService {
    /**
     * Inserts a new registration record
     * @param registrationDto Registration dto
     * @return Inserted registration record
     */
    RegistrationDto insert(RegistrationDto registrationDto);

    /**
     * Updates a registration record with specified in dto id
     * @param registrationDto Registration dto to update
     * @return Updated registration record
     */
    RegistrationDto update(RegistrationDto registrationDto);

    /**
     * Selects registration record by id
     * @param id Registration id
     * @return Selected registration with specified id
     */
    RegistrationDto select(Long id);

    /**
     * Removes registration record with specified id
     * @param id Registration id
     * @return Id of deleted registration record
     */
    Long delete(Long id);

    /**
     * Selects all registration records
     * @return List of all registration records
     */
    List<RegistrationDto> selectAll();

}
