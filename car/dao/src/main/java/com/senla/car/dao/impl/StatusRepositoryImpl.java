package com.senla.car.dao.impl;

import com.senla.car.dao.StatusRepository;
import com.senla.car.model.Status;
import com.senla.common.dao.CommonCrudRepository;

public class StatusRepositoryImpl extends CommonCrudRepository<Status, Long> implements StatusRepository {
}
