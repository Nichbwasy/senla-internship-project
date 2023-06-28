package com.senla.payment.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "payment_receipt")
public class PaymentReceipt {

    @Id
    @NotNull(message = "Payment receipt is mandatory!")
    @BsonProperty(value = "id")
    private String id;

    @NotNull(message = "Payment order number is mandatory!")
    @Size(min = 32, max = 32, message = "Order number must be length 32!")
    @BsonProperty(value = "order_number")
    private String orderNumber;

    @NotNull(message = "Payment receipts number is mandatory!")
    @Size(min = 32, max = 32, message = "Receipt number must be length 32!")
    @BsonProperty(value = "receipt_number")
    private String receiptNumber;

    @NotNull(message = "Payment time is mandatory!")
    @BsonProperty(value = "payment_time")
    private Timestamp paymentTime;

    @NotNull(message = "Amount is mandatory!")
    @BsonProperty(value = "amount")
    private BigDecimal amount;

}
