package com.senla.rental.service.mappers;

import com.senla.rental.dto.RentalRateDto;
import com.senla.rental.model.RentalRate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RentalRateMapper {

    RentalRate mapToModel(RentalRateDto rentalRateDto);
    RentalRateDto mapToDto(RentalRate rentalRate);
    void updateModel(RentalRateDto dto, @MappingTarget RentalRate model);

}
