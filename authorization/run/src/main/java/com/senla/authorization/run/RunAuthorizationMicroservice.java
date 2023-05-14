package com.senla.authorization.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.senla.authorization.model",
        "com.senla.authorization.dao",
        "com.senla.authorization.service",
        "com.senla.authorization.controller"
})
@EnableJpaRepositories(basePackages = {
        "com.senla.authorization.dao"
})
@EntityScan(basePackages = {
        "com.senla.authorization.model"
})
public class RunAuthorizationMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(RunAuthorizationMicroservice.class);
    }

}
