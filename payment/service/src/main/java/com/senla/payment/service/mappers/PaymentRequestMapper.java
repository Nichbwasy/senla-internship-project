package com.senla.payment.service.mappers;

import com.senla.payment.dto.PaymentRequestDto;
import com.senla.payment.model.PaymentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentRequestMapper {

    PaymentRequest mapToModel(PaymentRequestDto paymentRequestDto);
    PaymentRequestDto mapToDto(PaymentRequest paymentRequest);

}
