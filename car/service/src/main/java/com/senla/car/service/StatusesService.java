package com.senla.car.service;

import com.senla.car.dto.StatusDto;

import java.util.List;

public interface StatusesService {
    StatusDto insert(StatusDto statusDto);
    StatusDto update(StatusDto statusDto);
    StatusDto select(Long id);
    Long delete(Long id);
    List<StatusDto> selectAll();
}
