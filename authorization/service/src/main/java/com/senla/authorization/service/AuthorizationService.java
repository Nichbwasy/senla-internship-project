package com.senla.authorization.service;

import com.senla.authorization.dto.registrations.JwtTokensResponseDto;
import com.senla.authorization.dto.registrations.LogInUserDto;

public interface AuthorizationService {
    JwtTokensResponseDto logInUser(LogInUserDto dto);
}
