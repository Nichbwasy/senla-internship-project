package com.senla.car.service.impl;

import com.senla.car.dao.StatusesRepository;
import com.senla.car.dto.StatusDto;
import com.senla.car.model.Status;
import com.senla.car.service.StatusesService;
import com.senla.car.service.mappers.StatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class StatusesServiceImpl implements StatusesService {

    @Autowired
    private StatusesRepository statusesRepository;

    @Autowired
    private StatusMapper statusMapper;

    @Override
    @Transactional
    public StatusDto insert(StatusDto statusDto) {
        Status status = statusMapper.mapToModel(statusDto);
        status = statusesRepository.insert(status);
        log.info("A new car status has been added into database. {}.", status);
        return statusMapper.mapToDto(status);
    }

    @Override
    @Transactional
    public StatusDto update(StatusDto statusDto) {
        Status status = statusMapper.mapToModel(statusDto);
        status = statusesRepository.update(status);
        log.info("The car status with id '{}' has been updated in database. {}.", status.getId(), status);
        return statusMapper.mapToDto(status);
    }

    @Override
    public StatusDto select(Long id) {
        Status status = statusesRepository.select(id);
        log.info("The car status with id '{}' has been found in database.", status.getId());
        return statusMapper.mapToDto(status);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Long deletedId = statusesRepository.delete(id);
        log.info("The car status with id '{}' has been deleted in database", deletedId);
        return deletedId;
    }

    @Override
    public List<StatusDto> selectAll() {
        List<Status> statuses = statusesRepository.selectAll();
        log.info("All {} car statuses has been found in database.", statuses.size());
        return statuses.stream().map(s -> statusMapper.mapToDto(s)).collect(Collectors.toList());
    }

}
