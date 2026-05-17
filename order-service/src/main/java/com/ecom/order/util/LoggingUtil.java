package com.ecom.order.util;

import org.slf4j.Logger;
import org.slf4j.MDC;
import java.util.UUID;

public class LoggingUtil {

    public static final String CORRELATION_ID = "correlationId";
    public static final String REQUEST_ID = "requestId";
    public static final String SERVICE_NAME = "serviceName";
    public static final String ENDPOINT = "endpoint";
    public static final String METHOD = "method";
    public static final String CLIENT_IP = "clientIp";

    public static String generateCorrelationId() {
        return UUID.randomUUID().toString();
    }

    public static void setCorrelationId(String correlationId) {
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = generateCorrelationId();
        }
        MDC.put(CORRELATION_ID, correlationId);
    }

    public static String getCorrelationId() {
        String id = MDC.get(CORRELATION_ID);
        if (id == null) {
            id = generateCorrelationId();
            setCorrelationId(id);
        }
        return id;
    }

    public static void setRequestMetadata(String endpoint, String method, String clientIp, String serviceName) {
        MDC.put(ENDPOINT, endpoint);
        MDC.put(METHOD, method);
        MDC.put(CLIENT_IP, clientIp != null ? clientIp : "UNKNOWN");
        MDC.put(SERVICE_NAME, serviceName);
        MDC.put(REQUEST_ID, UUID.randomUUID().toString());
    }

    public static void clearContext() {
        MDC.clear();
    }

    public static void logRequestStart(Logger logger, String method, String endpoint, String queryParams) {
        logger.info("API Request started | method={} | endpoint={} | params={} | correlationId={}", 
            method, endpoint, queryParams, getCorrelationId());
    }

    public static void logRequestEnd(Logger logger, String method, String endpoint, int statusCode, long duration) {
        logger.info("API Request completed | method={} | endpoint={} | statusCode={} | durationMs={} | correlationId={}", 
            method, endpoint, statusCode, duration, getCorrelationId());
    }

    public static void logError(Logger logger, String method, String endpoint, Exception ex) {
        logger.error("API Request failed | method={} | endpoint={} | exception={} | correlationId={}", 
            method, endpoint, ex.getMessage(), getCorrelationId(), ex);
    }

    public static void logDatabaseOperation(Logger logger, String operation, String entity, long duration) {
        logger.info("Database operation | operation={} | entity={} | durationMs={} | correlationId={}", 
            operation, entity, duration, getCorrelationId());
    }

    public static void logExternalServiceCall(Logger logger, String serviceName, String endpoint, int statusCode, long duration) {
        logger.info("External service call | serviceName={} | endpoint={} | statusCode={} | durationMs={} | correlationId={}", 
            serviceName, endpoint, statusCode, duration, getCorrelationId());
    }

    public static void logBusinessLogic(Logger logger, String operation, String details) {
        logger.info("Business logic execution | operation={} | details={} | correlationId={}", 
            operation, details, getCorrelationId());
    }
}
