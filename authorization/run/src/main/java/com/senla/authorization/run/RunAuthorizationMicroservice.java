package com.senla.authorization.run;

import com.senla.starter.jwt.security.utils.utils.JwtTokenUtils;
import com.senla.starter.jwt.security.utils.validators.JwtTokenValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.senla.authorization",
        "com.senla.common.aspects",
        "com.senla.common.security",
        "com.senla.starter.jwt.security.utils"
})
@EnableJpaRepositories(basePackages = {"com.senla.authorization.dao"})
@EntityScan(basePackages = {"com.senla.authorization.model"})
public class RunAuthorizationMicroservice {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RunAuthorizationMicroservice.class);
        JwtTokenUtils jwtTokenUtils = context.getBean(JwtTokenUtils.class);
        JwtTokenValidator jwtTokenValidator = context.getBean(JwtTokenValidator.class);
        System.out.println();
    }

}
