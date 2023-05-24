package com.senla.car.dao.impl;

import com.senla.car.dao.ColorsRepository;
import com.senla.car.model.Color;
import com.senla.common.dao.CommonCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ColorsRepositoryImpl extends CommonCrudRepository<Color, Long> implements ColorsRepository {

    
}
