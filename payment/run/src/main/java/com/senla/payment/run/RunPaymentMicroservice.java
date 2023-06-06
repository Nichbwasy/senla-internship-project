package com.senla.payment.run;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.senla.payment",
        "com.senla.common.aspects",
        "com.senla.common.security",
        "com.senla.authorization.client",
        "com.senla.rental.client"
})
@EnableJpaRepositories(basePackages = {
        "com.senla.payment.dao"
})
@EntityScan(basePackages = {
        "com.senla.payment.model"
})
public class RunPaymentMicroservice {

    public static void main(String[] args) {
        SpringApplication.run(RunPaymentMicroservice.class);
    }

}
