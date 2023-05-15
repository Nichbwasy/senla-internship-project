package com.senla.car.service;

import com.senla.car.dto.TypeDto;

import java.util.List;

public interface TypesService {
    TypeDto insert(TypeDto type);
    TypeDto update(TypeDto type);
    TypeDto select(Long id);
    Long delete(Long id);
    List<TypeDto> selectAll();
}
