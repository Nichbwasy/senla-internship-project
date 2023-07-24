package com.senla.rental.service;

import com.senla.rental.dto.RequestStatusDto;

import java.util.List;

public interface RequestStatusesService {

    /**
     * Inserts a new request status record
     * @param requestStatusDto Request status dto to insert
     * @return Inserted request status record
     */
    RequestStatusDto insert(RequestStatusDto requestStatusDto);

    /**
     * Updates request status record 
     * @param requestStatusDto Request status to update
     * @return Updated request status record
     */
    RequestStatusDto update(RequestStatusDto requestStatusDto);

    /**
     * Selects request status record by id 
     * @param id Request status id
     * @return Selected by id record
     */
    RequestStatusDto select(Long id);

    /**
     * Deletes request status by id
     * @param id Request status id
     * @return Deleted request status id
     */
    Long delete(Long id);

    /**
     * Selects all request status records
     * @return List of all request status records
     */
    List<RequestStatusDto> selectAll();

    /**
     * Selects request record by name
     * @param name Request's name
     * @return Found request status
     */
    RequestStatusDto selectByName(String name);
}
