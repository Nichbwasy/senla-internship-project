package com.senla.payment.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@Document(value = "carentalreceipt")
public class CarRentalReceipt {

    @Id
    private Long id;

    @NotNull(message = "Request id is mandatory!")
    @Min(value = 1, message = "Request id can't be negative or zero!")
    @BsonProperty(value = "request_id")
    private Long requestId;

    @NotNull(message = "Price is mandatory!")
    @Min(value = 0, message = "Price can not be negative!")
    @BsonProperty(value = "price")
    private BigDecimal price;

    @NotNull(message = "Payment time is mandatory!")
    @BsonProperty(value = "payment_time")
    private Timestamp paymentTime;

}
