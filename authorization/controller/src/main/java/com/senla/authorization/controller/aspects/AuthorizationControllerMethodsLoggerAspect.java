package com.senla.authorization.controller.aspects;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class AuthorizationControllerMethodsLoggerAspect {

    @Around("execution(* com.senla.*..controller..*(..))")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        StringBuilder stringBuilder = new StringBuilder();
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        method.setAccessible(true);
        String methodName = method.getName();
        String methodClassName = method.getDeclaringClass().getName();
        Type returnType = method.getReturnType();
        Parameter[] methodParameters = method.getParameters();


        stringBuilder.append(String.format("\n\nCalling method '%s' for the authorization controller '%s'...\n\n", methodName, methodClassName));
        stringBuilder.append("Input parameters: \n");
        Arrays.stream(methodParameters).forEach(p ->
                stringBuilder.append(String.format("   [%s] type of %s. \n", p.getName(), p.getType().getSimpleName())));
        stringBuilder.append(String.format("Output parameter: %s. \n\n", returnType.getTypeName()));
        Date methodStartTime = new Date();

        pjp.proceed();

        Date methodEndTme = new Date();
        long workingTime = methodEndTme.getTime() - methodStartTime.getTime();
        stringBuilder.append(String.format("Method '%s' was worked %sms.", method.getName(), workingTime));
        log.info(stringBuilder.toString());
    }

}
