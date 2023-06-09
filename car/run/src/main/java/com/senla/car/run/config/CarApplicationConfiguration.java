package com.senla.car.run.config;

import com.senla.common.dao.configs.CommonRepositoryConfiguration;
import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = {
        "com.senla.car.dao",
        "com.senla.car.service",
        "com.senla.car.run",
        "com.senla.authorization.client",
        "com.senla.common.aspects",
        "com.senla.common.security",
        "com.senla.starter.jwt.security.utils"
        }, excludeFilters = {
        @ComponentScan.Filter(type= FilterType.ANNOTATION, value= EnableWebMvc.class)
})
@Import(CommonRepositoryConfiguration.class)
public class CarApplicationConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringLiquibase springLiquibase() {
        SpringLiquibase springLiquibase = new SpringLiquibase();
        springLiquibase.setChangeLog("classpath:liquibase/changelog-master.xml");
        springLiquibase.setDataSource(dataSource);
        return springLiquibase;
    }

}
