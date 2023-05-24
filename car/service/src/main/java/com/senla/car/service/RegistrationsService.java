package com.senla.car.service;

import com.senla.car.dto.RegistrationDto;

import java.util.List;

public interface RegistrationsService {
    RegistrationDto insert(RegistrationDto registrationDto);
    RegistrationDto update(RegistrationDto registrationDto);
    RegistrationDto select(Long id);
    Long delete(Long id);
    List<RegistrationDto> selectAll();

}
