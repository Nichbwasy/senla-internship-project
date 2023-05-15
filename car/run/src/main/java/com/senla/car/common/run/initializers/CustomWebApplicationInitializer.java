package com.senla.car.common.run.initializers;

import com.senla.car.common.run.config.CarApplicationConfiguration;
import com.senla.car.common.run.config.CarWebMvcConfig;
import com.senla.car.common.run.config.CarWebSecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class CustomWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
                CarApplicationConfiguration.class,
                CarWebSecurityConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {
                CarWebMvcConfig.class
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
        };
    }

}
