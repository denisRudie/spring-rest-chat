package ru.job4j.springrestchat.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {

    @Pointcut("execution(* ru.job4j.springrestchat.services.*.*(..))")
    public void selectAllMethodsAvailable() {

    }

    @Before("selectAllMethodsAvailable()")
    public void beforeRepoMethodInvocation(JoinPoint jp) {
        System.out.println("invocation of method: " + jp.getSignature());
    }
}
