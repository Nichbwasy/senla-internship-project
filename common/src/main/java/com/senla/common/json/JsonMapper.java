package com.senla.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JsonMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String objectToJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            log.warn("Can't parse object! " + e.getMessage());
            throw new RuntimeException("Can't parse object! " + e.getMessage());
        }
    }
}
