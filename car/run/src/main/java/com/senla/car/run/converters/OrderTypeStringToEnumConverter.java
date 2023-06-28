package com.senla.car.run.converters;

import com.senla.common.constants.ordering.OrderType;
import org.springframework.core.convert.converter.Converter;

public class OrderTypeStringToEnumConverter implements Converter<String, OrderType> {
    @Override
    public OrderType convert(String source) {
        return OrderType.valueOf(source.toUpperCase());
    }
}
