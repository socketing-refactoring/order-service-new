package com.jeein.order;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final String SWAGGER_PATH = "/api/v1/orders/api/";
    private static final String UPLOAD_PATH = "/upload/";
    private static final long MAX_LOG_SIZE = 1024 * 10;
    private static final String ACTUATOR_PATH = "/actuator";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isSwaggerRequest(request) || isActuatorRequest(request) || isImageRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);

        logRequest(requestWrapper);
        logResponse(responseWrapper);

        responseWrapper.copyBodyToResponse();
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        log.info("Request URI: {}, Method: {}", request.getRequestURI(), request.getMethod());

        // 요청 헤더 로깅
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            log.info(
                    "Request Header: {} = {}",
                    headerName,
                    Collections.list(request.getHeaders(headerName)));
        }

        // 요청 본문 로깅
        byte[] requestBody = request.getContentAsByteArray();
        if (requestBody.length <= MAX_LOG_SIZE) {
            log.info("Request Body: {}", new String(requestBody, StandardCharsets.UTF_8));
        } else {
            log.info("Request Body: [Data too large to log]");
        }
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        log.info("Response Status: {}", response.getStatus());

        // 응답 헤더 로깅
        response.getHeaderNames()
                .forEach(
                        headerName ->
                                log.info(
                                        "Response Header: {} = {}",
                                        headerName,
                                        response.getHeaders(headerName)));

        // 응답 본문 로깅
        byte[] responseBody = response.getContentAsByteArray();
        if (responseBody.length <= MAX_LOG_SIZE) {
            log.info("Response Body: {}", new String(responseBody, StandardCharsets.UTF_8));
        } else {
            log.info("Response Body: [Data too large to log]");
        }
    }

    private boolean isSwaggerRequest(HttpServletRequest request) {
        return request.getRequestURI().contains(SWAGGER_PATH);
    }

    private boolean isActuatorRequest(HttpServletRequest request) {
        return request.getRequestURI().contains(ACTUATOR_PATH);
    }

    private boolean isImageRequest(HttpServletRequest request) {
        return request.getRequestURI().contains(UPLOAD_PATH);
    }
}
