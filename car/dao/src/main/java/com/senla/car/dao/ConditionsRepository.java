package com.senla.car.dao;

import com.senla.car.model.Condition;
import com.senla.common.dao.CrudRepository;

public interface ConditionsRepository extends CrudRepository<Condition, Long> {

    /**
     * Checks car condition existence
     * @param name Car condition name
     * @return Returns true if car condition exists otherwise false
     */
    Boolean existsByName(String name);

}
