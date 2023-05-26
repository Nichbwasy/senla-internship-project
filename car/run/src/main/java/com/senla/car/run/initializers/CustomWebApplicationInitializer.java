package com.senla.car.run.initializers;

import com.senla.car.run.config.CarApplicationConfiguration;
import com.senla.car.run.config.CarWebMvcConfig;
import com.senla.car.run.config.CarWebSecurityConfig;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class CustomWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
                CarApplicationConfiguration.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {
                SpringDocConfiguration.class,
                CarWebMvcConfig.class,
                CarWebSecurityConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {
                "/",
                "/cars", "/cars/status", "/cars/filter", "/cars/**",
                "/colors", "/colors/**",
                "/conditions", "/conditions/**",
                "/registrations", "/registrations/**",
                "/statuses", "/statuses/**",
                "/types", "/types/**",
                "/swagger-ui.html", "/swagger-ui/**", "/v3/**", "/error", "/favicon.ico"
        };
    }

}
