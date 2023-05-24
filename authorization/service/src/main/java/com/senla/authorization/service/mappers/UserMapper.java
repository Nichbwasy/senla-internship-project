package com.senla.authorization.service.mappers;


import com.senla.authorization.dto.UserDataDto;
import com.senla.authorization.dto.registrations.UserRegistrationDataDto;
import com.senla.authorization.model.UserData;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDataDto mapToDto(UserData user);
    UserData mapToModel(UserDataDto userDto);

    UserData mapRegisterFormToUser(UserRegistrationDataDto dto);

}
