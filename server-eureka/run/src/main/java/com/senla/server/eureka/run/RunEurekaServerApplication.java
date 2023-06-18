package com.senla.server.eureka.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

@EnableEurekaServer
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.senla.server.eureka"
})
public class RunEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RunEurekaServerApplication.class);
    }

}
