package com.senla.payment.run;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.senla.payment",
        "com.senla.common.aspects",
        "com.senla.common.security",
        "com.senla.authorization.client",
        "com.senla.starter.jwt.security.utils",
        "com.senla.rental.client"
})
@EnableMongoRepositories(basePackages = {
        "com.senla.payment.dao"
})
public class RunPaymentMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(RunPaymentMicroservice.class);
    }

}
