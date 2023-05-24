package com.senla.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto implements Serializable {
    private Long id;
    private Long userId;
    private Long carId;
    private Timestamp startTime;
    private Timestamp endTime;
    private RequestRejectionDto requestRejection;
    private RequestStatusDto requestStatus;

}
