package com.senla.payment.dto.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetPayedRequestStatusFormDto {

    private Long requestId;
    private Long newRequestStatusId;

}
