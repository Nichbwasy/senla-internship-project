package com.senla.rental.service;

import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.dto.controller.CreateCarRefundFormDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CarsRefundsService {

    /**
     * Inserts a new refund record
     * @param carRefundDto Refund dto to insert
     * @return Inserted refund record
     */
    CarRefundDto insert(CarRefundDto carRefundDto);

    /**
     * Updates refund record
     * @param carRefundDto Refund to update
     * @return Updated refund record
     */
    CarRefundDto update(CarRefundDto carRefundDto);

    /**
     * Selects refund record by id
     * @param id Refund id
     * @return Selected by id record
     */
    CarRefundDto select(Long id);

    /**
     * Deletes refund by id
     * @param id Refund id
     * @return Deleted refund id
     */
    Long delete(Long id);

    /**
     * Selects all refund records
     * @return List of all refund records
     */
    List<CarRefundDto> selectAll();

    /**
     * Show page of car refunds
     * @param page Page number
     * @return List of car refunds for the specified page
     */
    List<CarRefundDto> selectRefundsPage(Integer page);

    /**
     * Creates a new car refund record
     * @param carRefundDto Car refund form
     * @return Created car refund record
     */
    CarRefundDto createCarRefund(@Valid CreateCarRefundFormDto carRefundDto);
}
