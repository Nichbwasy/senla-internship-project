package com.senla.authorization.service;

import com.senla.authorization.dto.AuthorityDto;

import java.util.List;

public interface AuthorityService {
    /**
     * Inserts a new authority record
     * @param authorityDto Record to insert
     * @return Inserted record
     */
    AuthorityDto insert(AuthorityDto authorityDto);

    /**
     * Updates existed authority record with id specified in dto
     * @param authorityDto Record to update
     * @return Updated record
     */
    AuthorityDto update(AuthorityDto authorityDto);

    /**
     * Gets authority record by id if it exists
     * @param id Id record to get
     * @return Selected authority
     */
    AuthorityDto select(Long id);

    /**
     * Removes authority record by id if it exists
     * @param id Id of record to delete
     * @return Deleted record's id
     */
    Long delete(Long id);

    /**
     * Returns authority all records
     * @return List of all records
     */
    List<AuthorityDto> selectAll();
}
