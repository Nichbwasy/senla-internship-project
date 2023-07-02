package com.senla.common.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
public class JsonMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String objectToJson(Object o) {
        try {
            if (o == null) {
                log.warn("Unable parse object ot json! Object is null!");
                throw new NullPointerException("Unable parse object ot json! Object is null!");
            }

            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            log.warn("Can't parse object! " + e.getMessage());
            throw new RuntimeException("Can't parse object! " + e.getMessage());
        }
    }
    public static <T> T jsonToObject(String json, Class<T> cl) {
        try {
            if (json == null) {
                log.warn("Unable parse json to the object! Json is null!");
                throw new NullPointerException("Unable parse json to the object! Json is null!");
            }

           return objectMapper.readValue(json, cl);
        } catch (JsonMappingException e) {
            log.error("Json mapping exception while parsing gson to the object! {}", e.getMessage());
            throw new RuntimeException(
                    String.format("Json mapping exception while parsing gson to the object! %s", e.getMessage())
            );
        } catch (JsonProcessingException e) {
            log.error("Processing exception while parsing json to the object! {}", e.getMessage());
            throw new RuntimeException(
                    String.format("Processing exception while parsing json to the object! %s", e.getMessage())
            );
        } catch (Exception e) {
            log.error("Exception while parsing json to the object! {}", e.getMessage());
            throw new RuntimeException(String.format("Exception while parsing json to the object! %s", e.getMessage()));
        }
    }
}
