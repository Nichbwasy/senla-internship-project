package com.senla.car.service.mappers;

import com.senla.car.dto.ColorDto;
import com.senla.car.model.Color;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColorsMapper {

    ColorDto mapToDto(Color color);
    Color mapToModel(ColorDto colorDto);

}
