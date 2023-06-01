package com.senla.authorization.service;

import com.senla.starter.jwt.security.utils.dto.AccessRefreshTokensDto;

public interface TokenService {
    AccessRefreshTokensDto refreshTokens(AccessRefreshTokensDto accessRefreshTokensDto);
}
