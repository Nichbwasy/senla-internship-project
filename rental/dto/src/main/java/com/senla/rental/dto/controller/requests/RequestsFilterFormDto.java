package com.senla.rental.dto.controller.requests;

import com.senla.rental.dto.controller.requests.ordering.RequestOrderingFieldDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestsFilterFormDto {

    private Boolean filterByTimeDiapason = false;
    private Boolean filterByRequestStatus = false;
    private Boolean enableOrdering = false;

    private String minTime;
    private String maxTime;
    private List<String> requestStatusesNames;

    private List<RequestOrderingFieldDto> orderingFields;
}
