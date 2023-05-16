package com.senla.rental.dto.controller;

import com.senla.common.formaters.TimestampFormatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarOrderingRequestDto {

    private Long carId;
    private Timestamp startTime;
    private Timestamp endTimme;
    public CarOrderingRequestDto(PostmanCarOrderingRequestDto postmanDto) {
        this.carId = postmanDto.getCarId();
        this.startTime = TimestampFormatter.formatToTimestamp(postmanDto.getStartTime());
        this.endTimme = TimestampFormatter.formatToTimestamp(postmanDto.getEndTimme());
    }
}
