package com.senla.rental.service;

import com.senla.rental.dto.RequestStatusDto;

import java.util.List;

public interface RequestStatusesService {
    RequestStatusDto insert(RequestStatusDto requestStatusDto);
    RequestStatusDto update(RequestStatusDto requestStatusDto);
    RequestStatusDto select(Long id);
    Long delete(Long id);
    List<RequestStatusDto> selectAll();
    RequestStatusDto selectByName(String name);
}
