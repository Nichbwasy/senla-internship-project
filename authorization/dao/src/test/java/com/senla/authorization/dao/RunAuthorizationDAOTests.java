package com.senla.authorization.dao;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = {
        "com.senla.authorization.dao",
        "com.senla.authorization.model"
})
@EntityScan(basePackages = {"com.senla.authorization.model"})
public class RunAuthorizationDAOTests {
}
