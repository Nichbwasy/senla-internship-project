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
@ComponentScan(basePackages = {
        "com.senla.car.dao",
        "com.senla.car.service",
        "com.senla.car.run",
        "com.senla.authorization.client"
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

    @Bean(name = "mvcHandlerMappingIntrospector")
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

    //------------------------------------------- swagger???
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
            return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        RequestMappingHandlerMapping mapping = new RequestMappingHandlerMapping();
        // add properties here
        return mapping;
    }

}
