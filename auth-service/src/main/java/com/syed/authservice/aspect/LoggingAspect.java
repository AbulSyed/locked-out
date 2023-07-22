package com.syed.authservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order(1)
@Aspect
@Component
public class LoggingAspect {

    @Before("com.syed.authservice.aspect.expression.AopExpressions.forAllControllers()")
    public void appLogger(JoinPoint joinPoint) {
        String signature = joinPoint.getSignature().toShortString();
        log.info("Entering {}", signature);
    }
}
