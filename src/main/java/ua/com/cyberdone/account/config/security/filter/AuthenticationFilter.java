package ua.com.cyberdone.account.config.security.filter;

import ua.com.cyberdone.account.config.security.JwtService;
import ua.com.cyberdone.account.repository.InvalidTokenRepository;
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
    protected InvalidTokenRepository invalidTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) {
        try {
            var token = parseJwt(request);
            if (nonNull(token)) {
                if (jwtService.validateJwtToken(token) &&
                        !invalidTokenRepository.existsByAccountUsernameAndInvalidToken(
                                jwtService.getUsername(token), token)) {
                    authenticate(request, jwtService.getUsername(token));
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Account authentication cannot be set:", e);
        }
    }

    public abstract void authenticate(HttpServletRequest request, String username);

    private String parseJwt(HttpServletRequest request) {
        var headerAuth = request.getHeader(AUTHORIZATION);
        return StringUtils.hasText(headerAuth) && headerAuth.startsWith(BEARER) ?
                headerAuth.substring(BEARER.length()) : null;
    }
}
