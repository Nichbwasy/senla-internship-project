package com.senla.authorization.service;

import com.senla.authorization.dto.registrations.JwtTokensResponseDto;
import com.senla.authorization.dto.registrations.LogInUserDto;

public interface AuthorizationService {
    /**
     * Authorized user in the system via login and password.
     * @param dto Login/password dto
     * @return Pair of access and refresh tokens
     */
    JwtTokensResponseDto logInUser(LogInUserDto dto);
}
