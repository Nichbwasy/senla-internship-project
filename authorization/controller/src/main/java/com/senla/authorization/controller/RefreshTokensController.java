package com.senla.authorization.controller;

import com.senla.authorization.service.TokenService;
import com.senla.common.security.dto.AccessRefreshTokensDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/tokens")
public class RefreshTokensController {

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<AccessRefreshTokensDto> refreshTokens(@RequestBody AccessRefreshTokensDto accessRefreshTokensDto) {
        log.info("Trying to refresh tokens '{}'...", accessRefreshTokensDto);
        return ResponseEntity.ok().body(tokenService.refreshTokens(accessRefreshTokensDto));
    }

}
