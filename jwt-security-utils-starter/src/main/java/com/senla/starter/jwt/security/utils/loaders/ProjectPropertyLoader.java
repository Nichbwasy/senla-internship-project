package com.senla.starter.jwt.security.utils.loaders;

import com.senla.starter.jwt.security.utils.configs.JwtTokenSecurityUtilsAutoConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class ProjectPropertyLoader {

    private static Properties properties;

    public static Properties loadProperties() {
        if (properties == null) {
            try {
                ClassLoader classLoader = JwtTokenSecurityUtilsAutoConfiguration.class.getClassLoader();
                InputStream applicationPropertiesStream = classLoader.getResourceAsStream("META-INF/application.properties");
                properties.load(applicationPropertiesStream);
                log.info("Properties for GreeterConfig has been loaded.");
                properties.forEach((k, v) -> log.info("Property '{}' -> '{}'", k, v));
            } catch (Exception e) {
                log.warn("Unable to load properties for the ProjectPropertyLoader! {}", e.getMessage());
            }
        }
        return properties;
    }

}
