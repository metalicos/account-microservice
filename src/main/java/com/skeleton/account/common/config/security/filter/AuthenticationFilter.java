package com.skeleton.account.common.config.security.filter;

import com.skeleton.account.common.config.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.nonNull;

@Slf4j
public abstract class AuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    @Autowired
    protected JwtUtils jwtUtils;
    @Autowired
    protected UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseJwt(request);
            if (nonNull(token) && jwtUtils.validateJwtToken(token)) {
                authenticate(request, token);
            }
        } catch (Exception e) {
            log.error("Account authentication cannot be set:", e);
        }
        filterChain.doFilter(request, response);
    }

    public abstract void authenticate(HttpServletRequest request, String token);

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(AUTHORIZATION);
        return StringUtils.hasText(headerAuth) &&
                headerAuth.startsWith(BEARER) ? headerAuth.substring(BEARER.length()) : null;
    }
}
