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
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "car_rental_receipt")
public class CarRentalReceipt {

    @Id
    private Long id;

    @NotNull(message = "User id is mandatory!")
    @Min(value = 0, message = "User id cant be lesser than 0!")
    @BsonProperty(value = "user_id")
    private Long userId;

    @NotNull(message = "User id is mandatory!")
    @Size(min = 3, max = 128, message = "User login must contains from 3 to 128 characters!")
    @BsonProperty(value = "user_login")
    private String userLogin;

    @NotNull(message = "Request id is mandatory!")
    @Min(value = 1, message = "Request id can't be negative or zero!")
    @BsonProperty(value = "request_id")
    private Long requestId;

    @NotNull(message = "Car id is mandatory!")
    @Min(value = 0, message = "User id cant be lesser than 0!")
    @BsonProperty(value = "car_id")
    private Long carId;

    @NotNull(message = "Car model is mandatory!")
    @Size(min = 3, max = 255, message = "Car model must contains from 3 to 255 characters!")
    @BsonProperty(value = "car_model")
    private String carModel;

    @NotNull(message = "Car number is mandatory!")
    @Size(min = 3, max = 64, message = "Car body number must contains from 3 to 64 characters!")
    @BsonProperty(value = "car_body_number")
    private String carBodyNumber;

    @NotNull(message = "Price is mandatory!")
    @Min(value = 0, message = "Price can not be negative!")
    @BsonProperty(value = "price")
    private BigDecimal price;

    @NotNull(message = "Payment time is mandatory!")
    @BsonProperty(value = "payment_time")
    private Timestamp paymentTime;

}
