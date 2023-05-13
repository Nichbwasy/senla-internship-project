package com.senla.common.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.common.clients.convertors.ObjectToUrlEncodedConverter;
import com.senla.common.exception.client.MicroserviceClientException;
import com.senla.common.exception.client.MicroserviceResponseException;
import com.senla.common.security.api.providers.ApiMicroservicesTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
public abstract class MicroserviceClient {
    protected final RestTemplate restTemplate = new RestTemplate();
    protected String MICROSERVICE_NAME;
    protected String MICROSERVICE_URL;

    protected <T> T sendRequest(String path, HttpMethod method, Class<T> type, Map<String, String> headers, Object body) {
        String requestUrl = MICROSERVICE_URL + path;
        try {
            restTemplate.getMessageConverters().add(new ObjectToUrlEncodedConverter(new ObjectMapper()));

            HttpEntity<?> httpEntity = generateHttpEntity(headers, body);
            ResponseEntity<T> response = restTemplate.exchange(requestUrl, method, httpEntity, type);

            T responseBody = response.getBody();

            if (responseBody == null) {
                throw new MicroserviceResponseException(
                        String.format("Failed response from '%s' microservice! Request url: '%s'.",
                                MICROSERVICE_NAME, requestUrl)
                );
            }
            log.info("Request of '{}' to the '{}' has been sent successfully", MICROSERVICE_NAME, requestUrl);
            return responseBody;
        } catch (Exception e) {
            log.error("Microservice '{}' client exception! {}.", MICROSERVICE_NAME, e.getMessage());
            throw new MicroserviceClientException(
                    String.format("Microservice '%s' client exception! %s.", MICROSERVICE_NAME, e.getMessage())
            );
        }
    }

    private HttpEntity<?> generateHttpEntity(Map<String, String> headers, Object body) {
        String microserviceAccessToken = ApiMicroservicesTokenProvider.generateMicroserviceAccessToken();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(microserviceAccessToken);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (headers != null) headers.keySet().forEach(h -> httpHeaders.add(h, headers.get(h)));

        return new HttpEntity<>(body, httpHeaders);
    }


}
