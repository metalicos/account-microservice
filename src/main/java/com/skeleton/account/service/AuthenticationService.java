package com.skeleton.account.service;

import com.skeleton.account.common.config.security.JwtService;
import com.skeleton.account.common.exception.AccountReadException;
import com.skeleton.account.common.exception.AuthenticationException;
import com.skeleton.account.dto.LoginDto;
import com.skeleton.account.dto.LogoutDto;
import com.skeleton.account.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final InvalidTokenService invalidTokenService;
    private final JwtService jwtService;

    public TokenDto login(LoginDto loginDto) throws AuthenticationException {
        try {
            var token = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            var authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException ex) {
            throw new AuthenticationException("Bad Username or Password. Exception=" + ex.getMessage());
        }

        return TokenDto.builder()
                .tokenType("Bearer ")
                .token(jwtService.generateToken(loginDto.getEmail()))
                .tokenLiveTimeInSeconds(MILLISECONDS.toSeconds(jwtService.getJwtExpirationTimeInMs()))
                .build();
    }

    public TokenDto logout(LogoutDto logoutDto) throws AccountReadException {
        var token = logoutDto.getToken();
        var account = accountService.getAccount(jwtService.getUsername(token));
        invalidTokenService.invalidate(account, token);
        return jwtService.getEmptyToken();
    }
}
