package com.senla.authorization.service;

import com.senla.common.security.dto.AccessRefreshTokensDto;

public interface TokenService {
    AccessRefreshTokensDto refreshTokens(AccessRefreshTokensDto accessRefreshTokensDto);
}
