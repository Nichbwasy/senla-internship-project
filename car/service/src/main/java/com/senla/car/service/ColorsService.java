package com.senla.car.service;

import com.senla.car.dto.ColorDto;

import java.util.List;

public interface ColorsService {
    ColorDto insert(ColorDto colorDto);
    ColorDto update(ColorDto colorDto);
    ColorDto select(Long id);
    Long delete(Long id);
    List<ColorDto> selectAll();
}
