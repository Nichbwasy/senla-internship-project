package com.senla.rental.dao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = {
        "com.senla.rental.model",
        "com.senla.rental.dao"
})
@EntityScan(basePackages = {"com.senla.rental.model"})
public class RunRentalDaoTests {
    public static void main(String[] args) {
        SpringApplication.run(RunRentalDaoTests.class);
    }
}
