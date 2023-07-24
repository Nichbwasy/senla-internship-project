package com.senla.authorization.service;

import com.senla.starter.jwt.security.utils.dto.AccessRefreshTokensDto;

public interface TokenService {
    /**
     * Refresh a pair of access/refresh tokens (refresh token must be which the last created)
     * @param accessRefreshTokensDto Pair access/refresh tokens
     * @return New created a pair of access/refresh tokens
     */
    AccessRefreshTokensDto refreshTokens(AccessRefreshTokensDto accessRefreshTokensDto);
}
