package com.senla.rental.service.impl;

import com.senla.rental.dao.RefundCompensationRepository;
import com.senla.rental.dto.RefundCompensationDto;
import com.senla.rental.model.RefundCompensation;
import com.senla.rental.service.RefundCompensationsService;
import com.senla.rental.service.mappers.RefundCompensationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RefundCompensationsServiceImpl implements RefundCompensationsService {

    @Autowired
    private RefundCompensationRepository refundCompensationRepository;

    @Autowired
    private RefundCompensationMapper refundCompensationMapper;

    @Override
    @Transactional
    public RefundCompensationDto insert(RefundCompensationDto refundCompensationDto) {
        log.info("Trying to insert new refund compensation record...");
        RefundCompensation refundCompensation = refundCompensationMapper.mapToModel(refundCompensationDto);
        return refundCompensationMapper.mapToDto(refundCompensationRepository.save(refundCompensation));
    }

    @Override
    @Transactional
    public RefundCompensationDto update(RefundCompensationDto refundCompensationDto) {
        log.info("Trying to update the refund compensation record '{}'...", refundCompensationDto.getId());
        RefundCompensation refundCompensation = refundCompensationRepository.getReferenceById(refundCompensationDto.getId());
        refundCompensationMapper.updateModel(refundCompensationDto, refundCompensation);
        return refundCompensationMapper.mapToDto(refundCompensationRepository.save(refundCompensation));
    }

    @Override
    public RefundCompensationDto select(Long id) {
        log.info("Trying to select refund compensation record with id '{}'...", id);
        return refundCompensationMapper.mapToDto(refundCompensationRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        log.info("Trying to delete refund compensation record with id '{}'...", id);
        refundCompensationRepository.deleteById(id);
        return id;
    }

    @Override
    public List<RefundCompensationDto> selectAll() {
        log.info("Trying to select all refund compensations records...");
        return refundCompensationRepository
                .findAll()
                .stream()
                .map(rc -> refundCompensationMapper.mapToDto(rc))
                .collect(Collectors.toList());
    }
}
