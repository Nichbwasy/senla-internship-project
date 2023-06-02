package com.senla.starter.jwt.security.utils.configs;

import com.senla.starter.jwt.security.utils.authentication.JwtAuthenticationUtils;
import com.senla.starter.jwt.security.utils.loaders.ProjectPropertyLoader;
import com.senla.starter.jwt.security.utils.properties.JwtSecurityUtilsConfigurationProperties;
import com.senla.starter.jwt.security.utils.utils.JwtTokenUtils;
import com.senla.starter.jwt.security.utils.validators.JwtTokenValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Slf4j
@Configuration
@ConditionalOnClass(value = {
        JwtTokenUtils.class,
        JwtTokenValidator.class,
        JwtAuthenticationUtils.class
})
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(JwtSecurityUtilsConfigurationProperties.class)
public class JwtTokenSecurityUtilsAutoConfiguration {

    @Autowired
    private JwtSecurityUtilsConfigurationProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenUtils jwtTokenUtils() {
        log.info("Configuring JwtTokenUtils...");
        log.info("Loaded properties: " +
                "Access token secret - {}, Refresh token secret - {}."
                , properties.getAccessTokenSecret(), properties.getRefreshTokenSecret());

        Properties defaultProperties = ProjectPropertyLoader.loadProperties();

        return new JwtTokenUtils(
                properties.getAccessTokenSecret() == null ?
                        defaultProperties.getProperty("access.token.secret") : properties.getAccessTokenSecret(),
                properties.getRefreshTokenSecret() == null ?
                        defaultProperties.getProperty("refresh.token.secret") : properties.getRefreshTokenSecret()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtTokenValidator jwtTokenValidator() {
        log.info("Configuring JwtTokenValidator...");
        log.info("Loaded properties: " +
                        "Access token secret - {}, Refresh token secret - {}."
                , properties.getAccessTokenSecret(), properties.getRefreshTokenSecret());

        Properties defaultProperties = ProjectPropertyLoader.loadProperties();

        return new JwtTokenValidator(
                properties.getAccessTokenSecret() == null ?
                    defaultProperties.getProperty("access.token.secret") : properties.getAccessTokenSecret(),
                properties.getRefreshTokenSecret() == null ?
                        defaultProperties.getProperty("refresh.token.secret") : properties.getRefreshTokenSecret()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public JwtAuthenticationUtils jwtAuthenticationUtils() {
        log.info("Configuring JwtAuthenticationUtils...");
        return new JwtAuthenticationUtils();
    }

}
