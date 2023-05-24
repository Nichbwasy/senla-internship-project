package com.senla.rental.dto.controller.requests.ordering;

import com.senla.common.constants.ordering.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrderingFieldDto {

    private RequestOrderingFieldName fieldsName;
    private OrderType orderType;

}
