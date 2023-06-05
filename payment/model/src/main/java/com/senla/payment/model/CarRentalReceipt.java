package com.senla.payment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "carrentalreceipt")
public class CarRentalReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Request id is mandatory!")
    @Min(value = 1, message = "Request id can't be negative or zero!")
    @Column(name = "request_id", nullable = false)
    private Long requestId;

    @NotNull(message = "Price is mandatory!")
    @Min(value = 0, message = "Price can not be negative!")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @NotNull(message = "Payment time is mandatory!")
    @Column(name = "payment_time", nullable = false)
    private Timestamp paymentTime;

}
