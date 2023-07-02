package com.senla.email.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.senla.email",
        "com.senla.common.security",
        "com.senla.starter.jwt.security.utils"
})
@EnableJpaRepositories(basePackages = {"com.senla.email.dao"})
@EntityScan(basePackages = {"com.senla.email.model"})
public class RunEmailMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(RunEmailMicroservice.class);
    }

}
