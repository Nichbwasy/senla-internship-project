package com.senla.common.generators;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class StringGenerator {
    private final static Random RANDOM = new Random();
    private final static String DEFAULT_CHARSET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private final static Integer DEFAULT_LENGTH = 32;

    public static String generateString() {
        return generate(DEFAULT_CHARSET, DEFAULT_LENGTH);
    }

    public static String generateString(Integer length) {
        return generate(DEFAULT_CHARSET, length);
    }

    public static String generateString(String charset) {
        return generate(charset, DEFAULT_LENGTH);
    }

    public static String generateString(String charset, Integer length) {
        return generate(charset, length);
    }

    private static String generate(String charset, Integer length) {
        try {
            StringBuilder result = new StringBuilder();
            while (result.length() < length) {
                result.append(charset.charAt(RANDOM.nextInt(charset.length())));
            }
            return result.toString();
        } catch (Exception e) {
            log.error("Exception while generating receipt number!" + e.getMessage());
            throw new RuntimeException("Exception while generating receipt number!" + e.getMessage());
        }
    }
}
