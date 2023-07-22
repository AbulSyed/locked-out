package com.syed.identityservice.aspect.expression;

import org.aspectj.lang.annotation.Pointcut;

public class AopExpressions {

    @Pointcut("execution(* com.syed.identityservice.controller.*.*(..))")
    public void forAllControllers() {}

    @Pointcut("execution(* com.syed.identityservice.service.impl.*.*(..))")
    public void forAllServices() {}

    @Pointcut("forAllControllers() || forAllServices()")
    public void forAllControllersOrServices() {}
}
