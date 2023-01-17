package com.muslim.bookstorage.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAspect {

    @Around("@annotation(com.muslim.bookstorage.aop.LogMethod)")
    public Object aroundLoggingAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        System.out.println("Method Signature "
                + methodSignature);
        System.out.println("Return Type "
                + methodSignature.getReturnType());
        System.out.println("Method Name "
                + methodSignature.getName());

        Arrays.stream(proceedingJoinPoint.getArgs())
                .forEach(o ->
                        System.out.println("arg value: " + o.toString()));

        Object targetMethodResult = proceedingJoinPoint.proceed();

        System.out.println(targetMethodResult);

        return targetMethodResult;
    }
}
