package com.senla.rental.service.mappers;

import com.senla.rental.dto.RequestRejectionDto;
import com.senla.rental.model.RequestRejection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RequestRejectionMapper {

    RequestRejectionDto mapToDto(RequestRejection requestRejection);
    RequestRejection mapToModel(RequestRejectionDto requestRejectionDto);
    void updateModel(RequestRejectionDto requestRejectionDto, @MappingTarget RequestRejection requestRejection);

}
