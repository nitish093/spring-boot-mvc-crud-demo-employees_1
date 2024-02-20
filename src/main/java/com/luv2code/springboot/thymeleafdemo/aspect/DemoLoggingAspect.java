package com.luv2code.springboot.thymeleafdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.logging.Logger;

@Aspect
@Component
public class DemoLoggingAspect {

    // setup logger
    private Logger myLogger = Logger.getLogger(getClass().getName());

    // setup pointcut declarations
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.controller.*.*(..))")
    private void forControllerPackage(){}

    // do the same for service and dao
    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.service.*.*(..))")
    private void forServicePackage(){}

    @Pointcut("execution(* com.luv2code.springboot.thymeleafdemo.dao.*.*(..))")
    private void forDaoPackage(){}

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow(){}

    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint)
    {
        // display method we are calling

        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("====>> in @Before: calling method: "+theMethod);

        // display the argument to the method

        // get the arguments
        Object[] args = theJoinPoint.getArgs();

        // loop through and display args
        for (Object tempObj: args) {
            myLogger.info("=====>> argument: "+tempObj);
        }

    }

    // add @AfterReturning advice
    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "theResult")
    public void afterReturning(JoinPoint theJoinPoint, Object theResult)
    {
        // display method we are returning from

        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("====>> in @AfterReturning: calling method: "+theMethod);

        // displaying the data
        myLogger.info("=====>> result: "+theResult);
    }

}
