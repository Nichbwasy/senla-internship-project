package com.senla.car.dao;

import com.senla.car.model.Status;
import com.senla.common.dao.CrudRepository;

public interface StatusesRepository extends CrudRepository<Status, Long> {

    Boolean existsByName(String name);

}
