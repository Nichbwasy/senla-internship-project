package com.senla.car.common.run.config;

import com.senla.car.common.run.converters.OrderTypeStringToEnumConverter;
import com.senla.car.common.run.converters.OrderingFieldNameStringToEnumConverter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {
        "com.senla.car.controller"
})
public class CarWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new OrderingFieldNameStringToEnumConverter());
        registry.addConverter(new OrderTypeStringToEnumConverter());
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
