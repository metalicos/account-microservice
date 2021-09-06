package com.skeleton.account.config.security.filter;

import com.skeleton.account.config.security.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class TokenAuthenticationFilter extends AuthenticationFilter {

    public TokenAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        super(jwtService, userDetailsService);
    }

    @Override
    public void authenticate(HttpServletRequest request, String token) {
        var username = jwtService.getUsername(token);
        var userDetails = userDetailsService.loadUserByUsername(username);
        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
