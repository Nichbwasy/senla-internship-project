package com.senla.rental.service.mappers;

import com.senla.rental.dto.CarRefundDto;
import com.senla.rental.model.CarRefund;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CarRefundMapper {

    CarRefundDto mapToDto(CarRefund carRefund);
    CarRefund mapToModel(CarRefundDto carRefundDto);

    void updateModel(CarRefundDto carRefundDto, @MappingTarget CarRefund carRefund);

}
