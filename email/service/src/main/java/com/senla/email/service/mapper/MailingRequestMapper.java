package com.senla.email.service.mapper;

import com.senla.email.dto.MailingRequestDto;
import com.senla.email.model.MailingRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MailingRequestMapper {

    MailingRequestDto mapToDto(MailingRequest mailingRequest);

    MailingRequest mapToModel(MailingRequestDto mailingRequest);

}
