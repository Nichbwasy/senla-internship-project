package com.senla.email.service.mapper;

import com.senla.email.dto.RestorePasswordRequestDto;
import com.senla.email.model.RestorePasswordRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestorePasswordRequestMapper {

    RestorePasswordRequestDto mapToDto(RestorePasswordRequest model);
    RestorePasswordRequest mapToModel(RestorePasswordRequestDto dto);

}
