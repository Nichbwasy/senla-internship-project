package com.senla.rental.service.impl;

import com.senla.rental.dao.RequestStatusRepository;
import com.senla.rental.dto.RequestStatusDto;
import com.senla.rental.model.RequestStatus;
import com.senla.rental.service.RequestStatusesService;
import com.senla.rental.service.mappers.RequestStatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RequestStatusesServiceImpl implements RequestStatusesService {

    @Autowired
    private RequestStatusRepository requestStatusRepository;

    @Autowired
    private RequestStatusMapper requestStatusMapper;

    @Override
    @Transactional
    public RequestStatusDto insert(RequestStatusDto requestStatusDto) {
        log.info("Trying to insert a new request status record...");
        RequestStatus requestStatus = requestStatusMapper.mapToModel(requestStatusDto);
        return requestStatusMapper.mapToDto(requestStatusRepository.save(requestStatus));
    }

    @Override
    @Transactional
    public RequestStatusDto update(RequestStatusDto requestStatusDto) {
        log.info("Trying to update the request status record with id '{}'...", requestStatusDto.getId());
        RequestStatus requestStatus = requestStatusRepository.getReferenceById(requestStatusDto.getId());
        requestStatusMapper.updateModel(requestStatusDto, requestStatus);
        return requestStatusMapper.mapToDto(requestStatusRepository.save(requestStatus));
    }

    @Override
    public RequestStatusDto select(Long id) {
        log.info("Trying to select request status record with id '{}'...", id);
        return requestStatusMapper.mapToDto(requestStatusRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        log.info("Trying to delete request status record '{}'...", id);
        requestStatusRepository.deleteById(id);
        return id;
    }

    @Override
    public List<RequestStatusDto> selectAll() {
        log.info("Trying to select all requests statuses records...");
        return requestStatusRepository
                .findAll()
                .stream()
                .map(rs -> requestStatusMapper.mapToDto(rs))
                .collect(Collectors.toList());
    }

    @Override
    public RequestStatusDto selectByName(String name) {
        RequestStatus requestStatus = requestStatusRepository.findByName(name);
        log.info("Request status '{}' has been found.", name);
        return requestStatusMapper.mapToDto(requestStatus);
    }
}
