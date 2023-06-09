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

    private String id;
    private Long userId;
    private String userLogin;
    private Long requestId;
    private Long carId;
    private String carModel;
    private String carBodyNumber;
    private BigDecimal price;
    private Timestamp paymentTime;

}
