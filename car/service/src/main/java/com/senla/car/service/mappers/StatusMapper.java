package com.senla.car.service.mappers;

import com.senla.car.dto.StatusDto;
import com.senla.car.model.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {

    StatusDto mapToDto(Status status);
    Status mapToModel(StatusDto statusDto);

}
