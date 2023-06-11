package com.senla.rental.service.impl;

import com.senla.rental.dao.RentalRateRepository;
import com.senla.rental.dto.RentalRateDto;
import com.senla.rental.model.RentalRate;
import com.senla.rental.service.RentalRateService;
import com.senla.rental.service.mappers.RentalRateMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RentalRateServiceImpl implements RentalRateService {

    @Autowired
    private RentalRateRepository rentalRateRepository;

    @Autowired
    private RentalRateMapper rentalRateMapper;

    @Override
    @Transactional
    public RentalRateDto insert(RentalRateDto requestDto) {
        RentalRate rentalRate = rentalRateRepository.save(rentalRateMapper.mapToModel(requestDto));
        log.info("New rental rate record '{}' was inserted into database.", rentalRate);
        return rentalRateMapper.mapToDto(rentalRate);
    }

    @Override
    @Transactional
    public RentalRateDto update(RentalRateDto requestDto) {
        RentalRate rentalRate = rentalRateRepository.getReferenceById(requestDto.getId());
        rentalRateMapper.updateModel(requestDto, rentalRate);
        log.info("Rental rate with id '{}' has been updated.", rentalRate.getId());
        return rentalRateMapper.mapToDto(rentalRate);
    }

    @Override
    public RentalRateDto select(Long id) {
        RentalRate rentalRate = rentalRateRepository.getReferenceById(id);
        log.info("Rental rate with id '{}' has been found in repository.", id);
        return rentalRateMapper.mapToDto(rentalRate);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        rentalRateRepository.deleteById(id);
        log.info("Rental rate record with id '{}' has been removed.", id);
        return id;
    }

    @Override
    public List<RentalRateDto> selectAll() {
        List<RentalRate> rentalRates = rentalRateRepository.findAll();
        log.info("All '{}' rental rates have been found in repository.", rentalRates.size());
        return rentalRates
                .stream()
                .map(rr -> rentalRateMapper.mapToDto(rr))
                .collect(Collectors.toList());
    }
}
