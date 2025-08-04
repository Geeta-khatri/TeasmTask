package com.swagger.p1.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Aspect
@Component
public class aopDemo {
    
//     @Before("execution(* com.swagger.p1..*.*(..))")
//     public void logbefore(JoinPoint jp){
//         System.out.println("before method "+jp.getSignature().getName());
//     }
 
//     @AfterReturning("execution(* com.swagger.p1..*.*(..))")
//     public void logAfterReturn(JoinPoint jp){
// System.out.println("methos "+jp.getSignature().getName()+" returned successfully");
//     }
//     @Around("execution(* com.swagger.p1..*.*(..))")
//     public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable{
//   String methodName = joinPoint.getSignature().getName();
        
//         System.out.println("🔄 Around BEFORE method: " + methodName);

//         Object result = joinPoint.proceed();  // This calls the actual method

//         System.out.println("✅ Around AFTER method: " + methodName);
        
//         return result;  // Must return the method's return value
  //  }
}
