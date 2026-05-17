package com.ecom.user.util;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RequestLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingAspect.class);

    @Around("execution(* com.ecom.user.resource..*.*(..))")
    public Object logApiEndpoints(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }

        HttpServletRequest request = attributes.getRequest();
        String method = request.getMethod();
        String endpoint = request.getRequestURI();
        String queryString = request.getQueryString() != null ? request.getQueryString() : "";
        String clientIp = getClientIp(request);

        String correlationId = request.getHeader("X-Correlation-Id");
        LoggingUtil.setCorrelationId(correlationId);
        LoggingUtil.setRequestMetadata(endpoint, method, clientIp, "user-service");

        long startTime = System.currentTimeMillis();
        LoggingUtil.logRequestStart(logger, method, endpoint, queryString);

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;

            int statusCode = 200;
            if (result instanceof ResponseEntity) {
                statusCode = ((ResponseEntity<?>) result).getStatusCodeValue();
            }

            LoggingUtil.logRequestEnd(logger, method, endpoint, statusCode, duration);
            return result;
        } catch (Exception ex) {
            long duration = System.currentTimeMillis() - startTime;
            LoggingUtil.logError(logger, method, endpoint, ex);
            throw ex;
        } finally {
            LoggingUtil.clearContext();
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr();
        } else {
            clientIp = clientIp.split(",")[0].trim();
        }
        return clientIp;
    }
}
