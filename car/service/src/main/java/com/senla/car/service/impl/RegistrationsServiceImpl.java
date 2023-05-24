package com.senla.car.service.impl;

import com.senla.car.dao.RegistrationsRepository;
import com.senla.car.dto.RegistrationDto;
import com.senla.car.model.Registration;
import com.senla.car.service.RegistrationsService;
import com.senla.car.service.mappers.RegistrationsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class RegistrationsServiceImpl implements RegistrationsService {

    @Autowired
    private RegistrationsRepository registrationsRepository;

    @Autowired
    private RegistrationsMapper registrationsMapper;

    @Override
    @Transactional
    public RegistrationDto insert(RegistrationDto registrationDto) {
        Registration registration = registrationsMapper.mapToModel(registrationDto);
        registration = registrationsRepository.insert(registration);
        log.info("A new car registration has been inserted into database. {}.", registration);
        return registrationsMapper.mapToDto(registration);
    }

    @Override
    @Transactional
    public RegistrationDto update(RegistrationDto registrationDto) {
        Registration registration = registrationsMapper.mapToModel(registrationDto);
        registration = registrationsRepository.update(registration);
        log.info("The car registration with id '{}' has been updated. {}.", registration.getId(), registration);
        return registrationsMapper.mapToDto(registration);
    }

    @Override
    public RegistrationDto select(Long id) {
        Registration registration = registrationsRepository.select(id);
        log.info("The car registration with id '{}' has been selected from database.", id );
        return registrationsMapper.mapToDto(registration);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        Long deletedId = registrationsRepository.delete(id);
        log.info("The car registration with id '{}' has been removed from database.", deletedId);
        return deletedId;
    }

    @Override
    public List<RegistrationDto> selectAll() {
        List<Registration> registrations = registrationsRepository.selectAll();
        log.info("All {} car registrations has been selected in repository.", registrations.size());
        return registrations.stream().map(r -> registrationsMapper.mapToDto(r)).collect(Collectors.toList());
    }

}
