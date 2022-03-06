package com.frankmoley.lil.fid.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class CounterAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    private static final Map<String, Integer> countingMap = new HashMap<>();

    @Pointcut("@annotation(Countable)")
    public void executeCounter(){}

    @Before("executeCounter()")
    public void countCalls(JoinPoint joinPoint){
        final String methodName = joinPoint.getSignature().getDeclaringTypeName()
                +"."
                +joinPoint.getSignature().getName();

        if(countingMap.containsKey(methodName)){
            countingMap.put(methodName, countingMap.get(methodName)+1);
        }else{
            countingMap.put(methodName, 1);
        }

        final StringBuilder message = new StringBuilder("Current Counts: |");
        countingMap.forEach((k,v) -> message.append("K: ").append(k).append(" V: ").append(v));
        LOGGER.info(message.toString());

    }

}
