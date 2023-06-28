package com.senla.car.dao;

import com.senla.car.model.Type;
import com.senla.common.dao.CrudRepository;

public interface TypesRepository extends CrudRepository<Type, Long> {

    Boolean existsByName(String name);

}
