package com.senla.authorization.run;

import com.senla.authorization.service.mappers.RoleMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.senla.authorization.model",
        "com.senla.authorization.dao",
        "com.senla.authorization.service"
})
@EnableJpaRepositories(basePackages = {
        "com.senla.authorization.dao"
})
@EntityScan(basePackages = {
        "com.senla.authorization.model"
})
public class RunAuthorizationMicroservice {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(RunAuthorizationMicroservice.class);
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        RoleMapper roleMapper = applicationContext.getBean(RoleMapper.class);
        System.out.println();
    }

}
