package com.senla.car.dao.impl;

import com.senla.car.dao.StatusesRepository;
import com.senla.car.model.Status;
import com.senla.common.dao.CommonCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class StatusesRepositoryImpl extends CommonCrudRepository<Status, Long> implements StatusesRepository {

}
