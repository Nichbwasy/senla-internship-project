package com.senla.common.clients;

import com.senla.starter.jwt.security.utils.dto.AccessRefreshTokensDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RefreshTokensMicroserviceClient extends MicroserviceClient {

    public RefreshTokensMicroserviceClient(
            String microserviceName,
            String microserviceUrl
    ) {
        MICROSERVICE_NAME = microserviceName;
        MICROSERVICE_URL = microserviceUrl;
    }
    public AccessRefreshTokensDto refreshTokens(AccessRefreshTokensDto accessRefreshTokensDto) {
        String path = "/api/tokens";
        log.info("Sending a request to the '{}' from '{}' to get a new pair of access/refresh tokens.",
                MICROSERVICE_URL + path, MICROSERVICE_NAME);
        return this.sendRequest(path, HttpMethod.POST, AccessRefreshTokensDto.class, null, accessRefreshTokensDto);
    }

}
