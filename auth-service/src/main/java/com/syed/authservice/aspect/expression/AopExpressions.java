package com.syed.authservice.aspect.expression;

import org.aspectj.lang.annotation.Pointcut;

public class AopExpressions {

    @Pointcut("execution(* com.syed.authservice.controller.*.*(..))")
    public void forAllControllers() {}
}
