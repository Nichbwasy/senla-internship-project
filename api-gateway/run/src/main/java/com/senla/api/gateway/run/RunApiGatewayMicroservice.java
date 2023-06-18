package com.senla.api.gateway.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.senla.api.gateway"
})
public class RunApiGatewayMicroservice {

    // TODO: Try to fix 'Caused by: java.lang.ClassNotFoundException: com.fasterxml.jackson.databind.PropertyNamingStrategies'
    
    public static void main(String[] args) {
        SpringApplication.run(RunApiGatewayMicroservice.class);
    }

}
