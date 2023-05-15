package com.senla.car.service.mappers;

import com.senla.car.dto.RegistrationDto;
import com.senla.car.model.Registration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationsMapper {

    RegistrationDto mapToDto(Registration registration);
    Registration mapToModel(RegistrationDto registrationDto);

}
