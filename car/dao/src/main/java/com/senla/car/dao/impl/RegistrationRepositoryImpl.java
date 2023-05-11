package com.senla.car.dao.impl;

import com.senla.car.dao.RegistrationRepository;
import com.senla.car.model.Registration;
import com.senla.common.dao.CommonCrudRepository;

public class RegistrationRepositoryImpl extends CommonCrudRepository<Registration, Long> implements RegistrationRepository {
}
