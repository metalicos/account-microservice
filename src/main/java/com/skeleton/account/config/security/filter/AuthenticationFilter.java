package com.skeleton.account.config.security.filter;

import com.skeleton.account.config.security.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.nonNull;

@Slf4j
@AllArgsConstructor
public abstract class AuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    protected JwtService jwtService;
    protected UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            var token = parseJwt(request);
            if (nonNull(token) && jwtService.validateJwtToken(token)) {
                authenticate(request, token);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Account authentication cannot be set:", e);
        }
    }

    public abstract void authenticate(HttpServletRequest request, String token);

    private String parseJwt(HttpServletRequest request) {
        var headerAuth = request.getHeader(AUTHORIZATION);
        return StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER) ?
                headerAuth.substring(BEARER.length()) : null;
    }
}