package com.senla.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundCompensationDto implements Serializable {
    private Long id;
    private String title;
    private String text;
    private Boolean isPaid;
    private Double cost;

}
