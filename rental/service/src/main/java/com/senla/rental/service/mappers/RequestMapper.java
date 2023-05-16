package com.senla.rental.service.mappers;

import com.senla.rental.dto.RequestDto;
import com.senla.rental.model.Request;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    RequestDto mapToDto(Request request);
    Request mapToModel(RequestDto requestDto);

    void updateModel(RequestDto requestDto, @MappingTarget Request request);
}
