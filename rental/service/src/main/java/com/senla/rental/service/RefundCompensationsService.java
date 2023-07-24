package com.senla.rental.service;


import com.senla.rental.dto.RefundCompensationDto;

import java.util.List;

public interface RefundCompensationsService {

    /**
     * Inserts a new refund compensation record
     * @param refundCompensationDto Refund compensation dto to insert
     * @return Inserted refund compensation record
     */
    RefundCompensationDto insert(RefundCompensationDto refundCompensationDto);

    /**
     * Updates refund compensation record 
     * @param refundCompensationDto Refund compensation to update
     * @return Updated refund compensation record
     */
    RefundCompensationDto update(RefundCompensationDto refundCompensationDto);

    /**
     * Selects refund compensation record by id 
     * @param id Refund compensation id
     * @return Selected by id record
     */
    RefundCompensationDto select(Long id);

    /**
     * Deletes refund compensation by id
     * @param id Refund compensation id
     * @return Deleted refund compensation id
     */
    Long delete(Long id);

    /**
     * Selects all refund compensation records
     * @return List of all refund compensation records
     */
    List<RefundCompensationDto> selectAll();
}
