package com.senla.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRentalReceiptDto {

    private Long id;
    private Long requestId;
    private BigDecimal price;
    private Timestamp paymentTime;

}
