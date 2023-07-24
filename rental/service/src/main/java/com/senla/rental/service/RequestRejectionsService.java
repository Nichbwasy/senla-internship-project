package com.senla.rental.service;

import com.senla.rental.dto.RequestRejectionDto;

import java.util.List;

public interface RequestRejectionsService {

    /**
     * Inserts a new request rejection record
     * @param requestRejectionDto Request rejection dto to insert
     * @return Inserted request rejection record
     */
    RequestRejectionDto insert(RequestRejectionDto requestRejectionDto);

    /**
     * Updates request rejection record 
     * @param requestRejectionDto Request rejection to update
     * @return Updated request rejection record
     */
    RequestRejectionDto update(RequestRejectionDto requestRejectionDto);

    /**
     * Selects request rejection record by id 
     * @param id Request rejection id
     * @return Selected by id record
     */
    RequestRejectionDto select(Long id);

    /**
     * Deletes request rejection by id
     * @param id Request rejection id
     * @return Deleted request rejection id
     */
    Long delete(Long id);

    /**
     * Selects all request rejection records
     * @return List of all request rejection records
     */
    List<RequestRejectionDto> selectAll();
}
