package com.senla.authorization.service;

import com.senla.authorization.dto.AuthorityDto;

import java.util.List;

public interface AuthorityService {
    AuthorityDto insert(AuthorityDto authorityDto);
    AuthorityDto update(AuthorityDto authorityDto);
    AuthorityDto select(Long id);
    Long delete(Long id);
    List<AuthorityDto> selectAll();
}
