package com.senla.common.aspects;


import com.senla.common.annotations.LogMethodExecution;
import com.senla.common.exception.aspects.MethodLoggerAspectException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class MethodLoggerAspect {
    @Pointcut("@within(com.senla.common.annotations.LogMethodExecution)")
    public void methodsWithLogMethodExecutionAnnotation() {}

    @Pointcut("execution(* *(..))")
    public void anyMethod() {}

    @Around("anyMethod() && methodsWithLogMethodExecutionAnnotation()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        try {
            Method method = ((MethodSignature) pjp.getSignature()).getMethod();
            method.setAccessible(true);

            LogMethodExecution annotation = method.getDeclaredAnnotation(LogMethodExecution.class);
            if (annotation != null) {
                if (!annotation.enabled()) {
                    log.debug("Annotation @LogMethodExecution is disable. " +
                            "Skip logging for '{}' method", method.getName());
                    return pjp.proceed();
                }
            }

            return logMethodMetadata(pjp, method);

        } catch (Exception e) {
            log.error("Exception while logging method! {}", e.getMessage());
            throw new MethodLoggerAspectException(
                    String.format("Exception while logging method! %s", e.getMessage())
            );
        }

    }

    private Object logMethodMetadata(ProceedingJoinPoint pjp, Method method) throws Throwable {
        StringBuilder stringBuilder = new StringBuilder();

        String methodName = method.getName();
        String methodClassName = method.getDeclaringClass().getName();
        Type returnType = method.getReturnType();
        Parameter[] methodParameters = method.getParameters();

        stringBuilder.append(String.format("\n\nCalling method '%s' for the controller '%s'...\n\n", methodName, methodClassName));
        stringBuilder.append("Input parameters: \n");
        Arrays.stream(methodParameters).forEach(p ->
                stringBuilder.append(String.format("   [%s] type of %s. \n", p.getName(), p.getType().getSimpleName())));
        stringBuilder.append(String.format("Output parameter: %s. \n\n", returnType.getTypeName()));
        long methodStartTime = System.currentTimeMillis();

        Object result = pjp.proceed();

        long workingTime = System.currentTimeMillis() - methodStartTime;
        stringBuilder.append(String.format("Method '%s' worked for %sms.", method.getName(), workingTime));
        log.info(stringBuilder.toString());
        return result;
    }

}
