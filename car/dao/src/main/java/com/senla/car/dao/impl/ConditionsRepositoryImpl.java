package com.senla.car.dao.impl;

import com.senla.car.dao.ConditionsRepository;
import com.senla.car.model.Condition;
import com.senla.common.dao.CommonCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ConditionsRepositoryImpl extends CommonCrudRepository<Condition, Long> implements ConditionsRepository {

}
