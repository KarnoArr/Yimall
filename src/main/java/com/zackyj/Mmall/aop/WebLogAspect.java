package com.zackyj.Mmall.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Description
 * @Author zackyj
 * @Created IN com.zackyj.Mmall.aop ON 2021/7/31-周六.
 */
@Aspect
@Slf4j
@Component
public class WebLogAspect {
    @Pointcut("execution(public * com.zackyj.Mmall.controller..*.*(..))")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
        //收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求，可能需要引入javax.servlet-api 4.0.1依赖
        HttpServletRequest request = attributes.getRequest();
        log.info("==========================请求开始==========================");
        //日志中输出内容
        log.info("URL: " + request.getRequestURI().toString());
        log.info("HTTP_method: " + request.getMethod());
        log.info("IP:" + request.getRemoteAddr());
        log.info("Class_method: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        String argsInfo = joinPoint.getArgs().length == 0 ? "No args come" : Arrays.toString(joinPoint.getArgs());
        log.info("args: " + argsInfo);
    }

    @AfterReturning(returning = "res", pointcut = "webLog()")
    public void doAfterReturning(Object res) throws JsonProcessingException {
        log.info("RESPONSE:" + new ObjectMapper().writeValueAsString(res));
        log.info("==========================响应结束==========================");
    }
}
