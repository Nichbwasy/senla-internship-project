package com.senla.car.service;

import com.senla.car.dto.ColorDto;

import java.util.List;

public interface ColorsService {
    /**
     * Inserts a new color record
     * @param colorDto Color dto
     * @return Inserted color record
     */
    ColorDto insert(ColorDto colorDto);

    /**
     * Updates a color record with specified in dto id
     * @param colorDto Color dto to update
     * @return Updated color record
     */
    ColorDto update(ColorDto colorDto);

    /**
     * Selects color record by id
     * @param id Color id
     * @return Selected color with specified id
     */
    ColorDto select(Long id);

    /**
     * Removes color record with specified id
     * @param id Color id
     * @return Id of deleted color record
     */
    Long delete(Long id);

    /**
     * Selects all color records
     * @return List of all color records
     */
    List<ColorDto> selectAll();
}
