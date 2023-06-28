package com.senla.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReceiptDto {

    private String id;
    private String orderNumber;
    private String receiptNumber;
    private String responseTopicName;
    private Timestamp paymentTime;
    private BigDecimal amount;

}
