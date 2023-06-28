package com.senla.payment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "payment_request")
public class PaymentRequest {

    @Id
    @NotNull(message = "Payment request id is mandatory!")
    private String id;

    @NotNull(message = "Payment response topic name is mandatory!")
    @Size(min = 3, max = 128, message = "ayment response topic name must be length from 3 to 128 characters!")
    @BsonProperty(value = "response_topic_name")
    private String responseTopicName;

    @NotNull(message = "Payment order number is mandatory!")
    @Size(min = 64, max = 64, message = "Order number must be length 64!")
    @BsonProperty(value = "order_number")
    private String orderNumber;

    @NotNull(message = "Amount is mandatory!")
    @Min(value = 0, message = "Amount cant be lesser than 0!")
    @BsonProperty(value = "amount")
    private BigDecimal amount;

}
