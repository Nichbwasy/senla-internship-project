package com.senla.car.service.mappers;

import com.senla.car.dto.TypeDto;
import com.senla.car.model.Type;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    TypeDto mapToDto(Type type);
    Type mapToModel(TypeDto typeDto);

}
