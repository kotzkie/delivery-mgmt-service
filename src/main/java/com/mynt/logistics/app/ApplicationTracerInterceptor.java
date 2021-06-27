package com.mynt.logistics.app;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ApplicationTracerInterceptor implements HandlerInterceptor {
    public static final String CORRELATION_ID_PARAMETER = "x-correlation-id";
    public static final  String UNKNOWN_CORRELATION_ID = "none";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String correlationId = request.getHeader(CORRELATION_ID_PARAMETER);
        log.debug(correlationId);
        MDC.put(CORRELATION_ID_PARAMETER, correlationId == null ? UNKNOWN_CORRELATION_ID : correlationId);
        return  true;
    }
}
