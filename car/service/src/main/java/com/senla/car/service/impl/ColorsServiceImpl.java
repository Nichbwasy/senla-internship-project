package com.senla.car.service.impl;

import com.senla.car.dao.ColorsRepository;
import com.senla.car.dto.ColorDto;
import com.senla.car.model.Color;
import com.senla.car.service.ColorsService;
import com.senla.car.service.mappers.ColorsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ColorsServiceImpl implements ColorsService {

    @Autowired
    private ColorsRepository colorsRepository;

    @Autowired
    private ColorsMapper colorsMapper;

    @Override
    @Transactional
    public ColorDto insert(ColorDto colorDto) {
        Color color = colorsMapper.mapToModel(colorDto);
        color = colorsRepository.insert(color);
        log.info("A new car color has been inserted into database. {}", color);
        return colorsMapper.mapToDto(color);
    }

    @Override
    @Transactional
    public ColorDto update(ColorDto colorDto) {
        Color color = colorsMapper.mapToModel(colorDto);
        color = colorsRepository.update(color);
        log.info("The car color with id '{}' has been updated in database. {}", color.getId(), color);
        return colorsMapper.mapToDto(color);
    }

    @Override
    public ColorDto select(Long id) {
        Color color = colorsRepository.select(id);
        log.info("The color with id {} has been found in database. {}", id, color);
        return colorsMapper.mapToDto(color);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Long deletedId = colorsRepository.delete(id);
        log.info("Car color with id '{}' has been removed from database.", deletedId);
        return deletedId;
    }

    @Override
    public List<ColorDto> selectAll() {
        List<Color> colors = colorsRepository.selectAll();
        log.info("All {} cars colors has been found in database.", colors.size());
        return colors.stream().map(c -> colorsMapper.mapToDto(c)).collect(Collectors.toList());
    }

}
