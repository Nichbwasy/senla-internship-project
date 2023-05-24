package com.senla.rental.dto.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostmanCarOrderingRequestDto {
    private Long carId;
    private String startTime;
    private String endTimme;
}
