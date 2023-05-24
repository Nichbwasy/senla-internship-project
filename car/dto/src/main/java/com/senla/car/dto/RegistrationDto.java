package com.senla.car.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto implements Serializable {
    private Long id;
    private String number;
    private String model;
    private Integer releaseYear;
    private String bodyNumber;
    private Integer allowedMaxMass;
    private Integer unladenMass;
    private ColorDto color;
    private TypeDto type;
}
