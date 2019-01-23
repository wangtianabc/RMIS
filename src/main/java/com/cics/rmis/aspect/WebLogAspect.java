package com.cics.rmis.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 面向切面编程，记录Action日志
 */

@Aspect
@Component
public class WebLogAspect {
    private static final Log log = LogFactory.getLog(WebLogAspect.class);
    private String url = "";

    @Pointcut("execution(public * com.cics.rmis.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        this.url = request.getRequestURI();
        String infoStr = "Action start:" + request.getRequestURI() + ", time:" + System.currentTimeMillis() + ", ip:" + request.getRemoteAddr();
        // 记录下请求内容
        log.info(infoStr);
        log.info("Args : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        log.info("Response : " + ret);
        log.info("Action end:" + url + ", time:" + System.currentTimeMillis());
    }
}
