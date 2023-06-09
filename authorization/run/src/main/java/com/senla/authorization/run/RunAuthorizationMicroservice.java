package com.senla.authorization.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@EnableDiscoveryClient
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
        SpringApplication.run(RunAuthorizationMicroservice.class);
    }

}
