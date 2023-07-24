package com.senla.car.dao;

import com.senla.car.model.Status;
import com.senla.common.dao.CrudRepository;

public interface StatusesRepository extends CrudRepository<Status, Long> {

    /**
     * Checks car status existence
     * @param name Car status name
     * @return Returns true if car status exists otherwise false
     */
    Boolean existsByName(String name);

}
