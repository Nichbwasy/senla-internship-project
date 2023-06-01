package com.senla.common.clients;

import com.senla.starter.jwt.security.utils.dto.AccessRefreshTokensDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RefreshTokensMicroserviceClient extends MicroserviceClient {

    @Value("${microservice.name}")
    private String MICROSERVICE_NAME;
    @Value("${authorization.microservice.url}")
    private String MICROSERVICE_URL;

    public AccessRefreshTokensDto refreshTokens(AccessRefreshTokensDto accessRefreshTokensDto) {
        String path = "/api/tokens";
        log.info("Sending a request to the '{}' from '{}' to get a new pair of access/refresh tokens.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME);
        return this.sendRequest(path, HttpMethod.POST, AccessRefreshTokensDto.class, null, accessRefreshTokensDto);
    }

}
