package com.senla.car.service.impl;

import com.senla.car.dao.TypesRepository;
import com.senla.car.dto.TypeDto;
import com.senla.car.model.Type;
import com.senla.car.service.TypesService;
import com.senla.car.service.mappers.TypeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class TypesServiceImpl implements TypesService {

    @Autowired
    private TypesRepository typesRepository;

    @Autowired
    private TypeMapper typeMapper;

    @Override
    @Transactional
    public TypeDto insert(TypeDto typeDto) {
        Type type = typeMapper.mapToModel(typeDto);
        type = typesRepository.insert(type);
        return typeMapper.mapToDto(type);
    }

    @Override
    @Transactional
    public TypeDto update(TypeDto typeDto) {
        Type type = typeMapper.mapToModel(typeDto);
        type = typesRepository.update(type);
        log.info("A new car type has been added into the database. {}.", type);
        return typeMapper.mapToDto(type);
    }

    @Override
    public TypeDto select(Long id) {
        Type type = typesRepository.select(id);
        log.info("The car type with id '{}' has been selected from database.", id);
        return typeMapper.mapToDto(type);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Long deletedId = typesRepository.delete(id);
        log.info("The car type with id '{}' has been removed from database.", deletedId);
        return deletedId;
    }

    @Override
    public List<TypeDto> selectAll() {
        List<Type> types = typesRepository.selectAll();
        log.info("All {} car types has been found in database.", types.size());
        return types.stream().map(t -> typeMapper.mapToDto(t)).collect(Collectors.toList());
    }

}
