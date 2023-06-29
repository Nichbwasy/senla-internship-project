package com.senla.email.service.mapper;

import com.senla.email.dto.ConfirmationMailDto;
import com.senla.email.model.ConfirmationMail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfirmationMailMapper {

    ConfirmationMailDto mapToDto(ConfirmationMail confirmationMail);
    ConfirmationMail mapToModel(ConfirmationMailDto confirmationMailDto);

}
