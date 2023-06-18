package com.senla.payment.service.mappers;

import com.senla.payment.dto.PaymentReceiptDto;
import com.senla.payment.dto.PaymentRequestDto;
import com.senla.payment.model.PaymentReceipt;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentReceiptMapper {

    PaymentReceipt mapToModel(PaymentRequestDto paymentRequestDto);
    PaymentReceiptDto mapToDto(PaymentReceipt paymentRequest);

}
