package com.senla.email.service.mapper;

import com.senla.email.dto.EmailConfirmationCodeDto;
import com.senla.email.model.EmailConfirmationCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailConfirmationCodeMapper {

    EmailConfirmationCodeDto mapToDto(EmailConfirmationCode emailConfirmationCode);
    EmailConfirmationCode mapToModel(EmailConfirmationCode emailConfirmationCodeDto);

}
