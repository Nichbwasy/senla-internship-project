package com.senla.rental.dto.controller;

import com.senla.rental.dto.RefundCompensationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarRefundFormDto {

    private Long requestId;
    private RefundCompensationDto compensation;

}
