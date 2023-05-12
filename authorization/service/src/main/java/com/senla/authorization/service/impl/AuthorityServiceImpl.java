package com.senla.authorization.service.impl;

import com.senla.authorization.dao.AuthorityRepository;
import com.senla.authorization.dto.AuthorityDto;
import com.senla.authorization.model.Authority;
import com.senla.authorization.service.AuthorityService;
import com.senla.authorization.service.mappers.AuthorityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AuthorityServiceImpl implements AuthorityService {

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AuthorityMapper authorityMapper;

    @Override
    @Transactional
    public AuthorityDto insert(AuthorityDto authorityDto) {
        log.info("Trying to insert new authority '{}'...", authorityDto);
        Authority authority = authorityMapper.mapToModel(authorityDto);
        return authorityMapper.mapToDto(authorityRepository.save(authority));
    }

    @Override
    @Transactional
    public AuthorityDto update(AuthorityDto authorityDto) {
        log.info("Trying to update authority with id '{}' to '{}'...", authorityDto.getId(), authorityDto);
        Authority authority = authorityRepository.getReferenceById(authorityDto.getId());
        authorityMapper.updateModel(authorityDto, authority);
        return authorityMapper.mapToDto(authorityRepository.save(authority));
    }

    @Override
    public AuthorityDto select(Long id) {
        log.info("Trying to select the authority with id '{}'...", id);
        return authorityMapper.mapToDto(authorityRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        log.info("Trying to delete the authority with id '{}'...", id);
        authorityRepository.deleteById(id);
        return id;
    }

    @Override
    public List<AuthorityDto> selectAll() {
        log.info("Trying to get list all authorities...");
        return authorityRepository
                .findAll()
                .stream()
                .map(a -> authorityMapper.mapToDto(a))
                .collect(Collectors.toList());
    }
}
