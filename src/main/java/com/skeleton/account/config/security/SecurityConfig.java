package com.skeleton.account.config.security;

import com.skeleton.account.config.security.filter.AuthenticationFilter;
import com.skeleton.account.config.security.filter.CrossOriginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletResponse;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CyberdoneUserDetailsService cyberdoneUserDetailsService;
    private final AuthenticationFilter authFilter;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(13);
    }

    @Bean
    public CrossOriginFilter crossOriginFilter() {
        return new CrossOriginFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(cyberdoneUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, exception) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                                "Error: Unauthorized"))

                .and()
                .exceptionHandling()
                .accessDeniedHandler(
                        (request, response, e) -> response.sendError(HttpServletResponse.SC_FORBIDDEN,
                                "Error: Forbidden"))

                .and()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(crossOriginFilter(), CorsFilter.class)

                .authorizeRequests()
                .antMatchers("/accounts/authentication/**",
                        "/accounts/change/password",
                        "/accounts/registration")
                .permitAll()

                .anyRequest()
                .authenticated()

                .and()
                .logout()
                .logoutUrl("/accounts/authentication/logout")

                .logoutSuccessHandler(((request, response, authentication) -> {
                    response.setStatus(HttpServletResponse.SC_OK);
                    authentication.setAuthenticated(false);
                }))
                .permitAll();
    }
}
