package com.senla.rental.service.impl;

import com.senla.rental.dao.RequestRejectionRepository;
import com.senla.rental.dto.RequestRejectionDto;
import com.senla.rental.model.RequestRejection;
import com.senla.rental.service.RequestRejectionsService;
import com.senla.rental.service.mappers.RequestRejectionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RequestRejectionsServiceImpl implements RequestRejectionsService {

    @Autowired
    private RequestRejectionRepository requestRejectionRepository;
    @Autowired
    private RequestRejectionMapper requestRejectionMapper;

    @Override
    @Transactional
    public RequestRejectionDto insert(RequestRejectionDto requestRejectionDto) {
        log.info("Trying to insert new request rejection record...");
        RequestRejection requestRejection = requestRejectionMapper.mapToModel(requestRejectionDto);
        return requestRejectionMapper.mapToDto(requestRejectionRepository.save(requestRejection));
    }

    @Override
    @Transactional
    public RequestRejectionDto update(RequestRejectionDto requestRejectionDto) {
        log.info("Trying to update the request rejection record with id '{}'...", requestRejectionDto.getId());
        RequestRejection rejection = requestRejectionRepository.getReferenceById(requestRejectionDto.getId());
        requestRejectionMapper.updateModel(requestRejectionDto, rejection);
        return requestRejectionMapper.mapToDto(requestRejectionRepository.save(rejection));
    }

    @Override
    public RequestRejectionDto select(Long id) {
        log.info("Trying to select request rejection record with id '{}'...", id);
        return requestRejectionMapper.mapToDto(requestRejectionRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        log.info("Trying to delete request rejection with id '{}'...", id);
        requestRejectionRepository.deleteById(id);
        return id;
    }

    @Override
    public List<RequestRejectionDto> selectAll() {
        log.info("Trying to select all requests rejections records...");
        return requestRejectionRepository
                .findAll()
                .stream()
                .map(rr -> requestRejectionMapper.mapToDto(rr))
                .collect(Collectors.toList());
    }
}
