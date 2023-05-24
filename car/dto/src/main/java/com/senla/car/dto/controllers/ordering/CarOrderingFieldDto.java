package com.senla.car.dto.controllers.ordering;

import com.senla.common.constants.ordering.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarOrderingFieldDto {

    private CarOrderingFieldsNames fieldsName;
    private OrderType orderType = OrderType.ASC;

}
