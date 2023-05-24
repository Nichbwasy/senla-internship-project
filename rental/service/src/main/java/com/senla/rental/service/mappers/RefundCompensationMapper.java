package com.senla.rental.service.mappers;

import com.senla.rental.dto.RefundCompensationDto;
import com.senla.rental.model.RefundCompensation;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RefundCompensationMapper {

    RefundCompensationDto mapToDto(RefundCompensation refundCompensation);
    RefundCompensation mapToModel(RefundCompensationDto refundCompensationDto);

    void updateModel(RefundCompensationDto refundCompensationDto, @MappingTarget RefundCompensation refundCompensation);

}
