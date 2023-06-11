package com.senla.payment.service.mappers;

import com.senla.payment.dto.CarRentalReceiptDto;
import com.senla.payment.model.CarRentalReceipt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarRentalReceiptMapper {

    CarRentalReceipt mapToModel(CarRentalReceiptDto carRentalReceiptDto);

    CarRentalReceiptDto mapToDto(CarRentalReceipt carRentalReceipt);


}
