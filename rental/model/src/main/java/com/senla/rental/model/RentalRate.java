package com.senla.rental.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "rentalrate")
public class RentalRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Car type is mandatory!")
    @Column(name = "cartype", length = 255, nullable = false)
    private String carType;

    @NotBlank(message = "Car condition is mandatory!")
    @Column(name = "carcondition", length = 255, nullable = false)
    private String carCondition;

    @NotNull(message = "Rate per hour is mandatory!")
    @Min(value = 0, message = "Rate per hour can not be lesser than 0!")
    @Column(name = "rateperhour", nullable = false)
    private BigDecimal ratePerHour;

}
