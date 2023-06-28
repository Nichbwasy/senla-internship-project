package com.senla.car.service.impl;

import com.senla.car.dao.ConditionsRepository;
import com.senla.car.dto.ConditionDto;
import com.senla.car.model.Condition;
import com.senla.car.service.ConditionsService;
import com.senla.car.service.mappers.ConditionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ConditionsServiceImpl implements ConditionsService {

    @Autowired
    private ConditionsRepository conditionsRepository;

    @Autowired
    private ConditionMapper conditionMapper;

    @Override
    @Transactional
    public ConditionDto insert(ConditionDto conditionDto) {
        Condition condition = conditionMapper.mapToModel(conditionDto);
        condition = conditionsRepository.insert(condition);
        log.info("A new car condition has been inserted into database. {}.", condition);
        return conditionMapper.mapToDto(condition);
    }

    @Override
    @Transactional
    public ConditionDto update(ConditionDto conditionDto) {
        Condition condition = conditionMapper.mapToModel(conditionDto);
        condition = conditionsRepository.update(condition);
        log.info("The car condition with id '{}' has been updated. {}.", condition.getId(), condition);
        return conditionMapper.mapToDto(condition);
    }

    @Override
    public ConditionDto select(Long id) {
        Condition condition = conditionsRepository.select(id);
        log.info("Car condition with id '{}' has been selected from database. {}.", id, condition);
        return conditionMapper.mapToDto(condition);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Long deletedId = conditionsRepository.delete(id);
        log.info("Car condition with id '{}' has been removed from database.", deletedId);
        return deletedId;
    }

    @Override
    public List<ConditionDto> selectAll() {
        List<Condition> conditions = conditionsRepository.selectAll();
        log.info("All {} cae conditions has been selected from database.", conditions.size());
        return conditions.stream().map(c -> conditionMapper.mapToDto(c)).collect(Collectors.toList());
    }

    @Override
    public Boolean existsByName(String name) {
        Boolean exists = conditionsRepository.existsByName(name);
        if (exists) log.info("Car condition with name '{}' exists.", name);
        else log.info("Car condition with name '{}' not exists.", name);
        return exists;
    }
}
