package com.skeleton.account.common.config.security.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CrossOriginFilter extends OncePerRequestFilter {

    public static final String X_FORWARDED_HOST = "X-Forwarded-Host";
    private static final String ALLOWED_PATTERN = "*";
    private static final String TRUE = "true";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String host = request.getHeader(X_FORWARDED_HOST);

        if (!StringUtils.hasText(host)) {
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, TRUE);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_PATTERN);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_PATTERN);
            response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_PATTERN);
        }
        filterChain.doFilter(request, response);
    }
}
