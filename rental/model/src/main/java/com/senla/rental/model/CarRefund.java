package com.senla.rental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CarRefund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Car refund user id is mandatory!")
    @Min(value = 1, message = "Black list user id can't be lesser then 1!")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "Car refund car id is mandatory!")
    @Min(value = 0, message = "Car refund car id can't be negative!")
    @Column(name = "car_id", nullable = false)
    private Long carId;

    @NotNull(message = "Car refund start time is mandatory!")
    @Column(name = "start_using_time", nullable = false)
    private Timestamp startUsingTime;

    @NotNull(message = "Car refund end time is mandatory!")
    @Column(name = "end_using_time", nullable = false)
    private Timestamp endUsingTime;

    @NotNull(message = "Car refund end time is mandatory!")
    @Column(name = "refund_time", nullable = false)
    private Timestamp refundTime;

    @OneToOne(targetEntity = Request.class, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Request request;

    @OneToOne(targetEntity = RefundCompensation.class, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private RefundCompensation refundCompensation;

}
