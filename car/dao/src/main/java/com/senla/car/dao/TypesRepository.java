package com.senla.car.dao;

import com.senla.car.model.Type;
import com.senla.common.dao.CrudRepository;

public interface TypesRepository extends CrudRepository<Type, Long> {

    /**
     * Checks car type existence
     * @param name Car type name
     * @return Returns true if car type exists otherwise false
     */
    Boolean existsByName(String name);

}
