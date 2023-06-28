package com.senla.car.run.converters;

import com.senla.car.dto.controllers.ordering.CarOrderingFieldsNames;
import org.springframework.core.convert.converter.Converter;

public class OrderingFieldNameStringToEnumConverter implements Converter<String, CarOrderingFieldsNames> {
    @Override
    public CarOrderingFieldsNames convert(String source) {
        return CarOrderingFieldsNames.valueOf(source.toUpperCase());
    }
}
