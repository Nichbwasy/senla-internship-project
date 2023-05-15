package com.senla.car.service.mappers;

import com.senla.car.dto.ConditionDto;
import com.senla.car.model.Condition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConditionMapper {

    ConditionDto mapToDto(Condition condition);
    Condition mapToModel(ConditionDto conditionDto);

}
