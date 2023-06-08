package com.senla.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalRateDto {

    private Long id;
    private String carType;
    private String carCondition;
    private BigDecimal ratePerHour;

}
