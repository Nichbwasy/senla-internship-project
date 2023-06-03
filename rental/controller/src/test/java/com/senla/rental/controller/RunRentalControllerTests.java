package com.senla.rental.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.senla.rental.model",
        "com.senla.rental.dao",
        "com.senla.rental.service",
        "com.senla.rental.controller",
        "com.senla.authorization.client",
        "com.senla.car.client",
        "com.senla.starter.jwt.security.utils"
})
@EnableJpaRepositories(basePackages = {
        "com.senla.rental.dao"
})
@EntityScan(basePackages = {
        "com.senla.rental.model"
})
public class RunRentalControllerTests {

    public static void main(String[] args) {
        SpringApplication.run(RunRentalControllerTests.class);
    }

}
