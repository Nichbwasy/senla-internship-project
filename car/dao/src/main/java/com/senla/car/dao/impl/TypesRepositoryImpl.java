package com.senla.car.dao.impl;

import com.senla.car.dao.TypesRepository;
import com.senla.car.model.Type;
import com.senla.common.dao.CommonCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TypesRepositoryImpl extends CommonCrudRepository<Type, Long> implements TypesRepository {

}
