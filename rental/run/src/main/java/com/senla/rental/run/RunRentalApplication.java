package com.senla.rental.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.senla.rental",
        "com.senla.common.aspects",
        "com.senla.common.security",
        "com.senla.authorization.client",
        "com.senla.car.client"
})
@EnableJpaRepositories(basePackages = {
        "com.senla.rental.dao"
})
@EntityScan(basePackages = {
        "com.senla.rental.model"
})
public class RunRentalApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunRentalApplication.class);
    }
}
