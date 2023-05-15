package com.senla.car.service;

import com.senla.car.dto.ConditionDto;

import java.util.List;

public interface ConditionsService {
    ConditionDto insert(ConditionDto conditionDto);
    ConditionDto update(ConditionDto conditionDto);
    ConditionDto select(Long id);
    Long delete(Long id);
    List<ConditionDto> selectAll();
}
