package com.senla.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto implements Serializable {
    private Long id;
    private String description;
    private Double mileage;
    private RegistrationDto registration;
    private ConditionDto condition;
    private StatusDto status;
}
