package com.senla.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarRefundDto implements Serializable {
    private Long id;
    private Long userId;
    private Long carId;
    private Timestamp startUsingTime;
    private Timestamp endUsingTime;
    private Timestamp refundTime;
    private RequestDto request;
    private RefundCompensationDto refundCompensation;
}
