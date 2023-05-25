package com.senla.car.run.composnents;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;

@Component
public class EndpointsLogger {

    @EventListener
    public void endpointsLog(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);

        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();

        System.out.println(("\n\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=[ MAPPED ENDPOINTS ]=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n"));
        map.forEach((key, value) -> System.out.println(String.format("Mapping: %s | Controller endpoint: %s", key, value)));
        System.out.println(("\n\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n"));

    }
}
