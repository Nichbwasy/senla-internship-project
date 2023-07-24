package com.senla.rental.service;


import com.senla.rental.dto.RentalRateDto;

import java.util.List;

public interface RentalRateService {

    /**
     * Inserts a new rental rate record
     * @param requestDto Rental rate dto to insert
     * @return Inserted rental rate record
     */
    RentalRateDto insert(RentalRateDto requestDto);

    /**
     * Updates rental rate record 
     * @param requestDto Rental rate to update
     * @return Updated rental rate record
     */
    RentalRateDto update(RentalRateDto requestDto);

    /**
     * Selects rental rate record by id 
     * @param id Rental rate id
     * @return Selected by id record
     */
    RentalRateDto select(Long id);

    /**
     * Deletes rental rate by id
     * @param id Rental rate id
     * @return Deleted rental rate id
     */
    Long delete(Long id);

    /**
     * Selects all rental rate records
     * @return List of all rental rate records
     */
    List<RentalRateDto> selectAll();

}
