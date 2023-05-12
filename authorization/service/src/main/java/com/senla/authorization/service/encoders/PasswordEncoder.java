package com.senla.authorization.service.encoders;

import com.senla.authorization.service.exceptions.encoders.PasswordEncoderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PasswordEncoder {

    private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encodeString(String s) {
        try {
            log.info("Encoding a password string...");
            return passwordEncoder.encode(s);
        } catch (Exception e) {
            log.warn("Unable to encode a password string! {}.", e.getMessage());
            throw new PasswordEncoderException("Exception while encoding string! " + e.getMessage());
        }
    }

    public static Boolean match(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
