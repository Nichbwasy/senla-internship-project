package com.senla.email.service.mapper;

import com.senla.email.dto.RestorePasswordConfirmationCodeDto;
import com.senla.email.model.RestorePasswordConfirmationCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestorePasswordConfirmationCodeMapper {

    RestorePasswordConfirmationCodeDto mapToDto(RestorePasswordConfirmationCode model);
    RestorePasswordConfirmationCode mapToModel(RestorePasswordConfirmationCodeDto dto);

}
