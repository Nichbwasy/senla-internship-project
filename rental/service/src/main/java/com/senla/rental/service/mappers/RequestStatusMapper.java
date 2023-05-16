package com.senla.rental.service.mappers;

import com.senla.rental.dto.RequestStatusDto;
import com.senla.rental.model.RequestStatus;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RequestStatusMapper {

    RequestStatusDto mapToDto(RequestStatus requestStatus);
    RequestStatus mapToModel(RequestStatusDto requestStatusDto);
    void updateModel(RequestStatusDto requestStatusDto, @MappingTarget RequestStatus requestStatus);
}
