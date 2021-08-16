package com.skeleton.account.service;

import com.skeleton.account.common.config.security.JwtUtils;
import com.skeleton.account.common.exception.AccountNotFoundException;
import com.skeleton.account.common.exception.AuthenticationException;
import com.skeleton.account.common.exception.InvalidTokenException;
import com.skeleton.account.dto.AccountDto;
import com.skeleton.account.dto.LoginDto;
import com.skeleton.account.dto.LogoutDto;
import com.skeleton.account.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final InvalidTokenService invalidTokenService;
    private final JwtUtils jwtUtils;

    public TokenDto login(LoginDto loginDto) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (BadCredentialsException ex) {
            throw new AuthenticationException("Bad Username or Password. Exception=" + ex.getMessage());
        }
        return TokenDto.builder()
                .token(jwtUtils.generateToken(loginDto.getEmail()))
                .tokenType("Bearer ")
                .build();
    }

    public TokenDto logout(LogoutDto logoutDto)
            throws AuthenticationException, AccountNotFoundException, InvalidTokenException {
        String token = logoutDto.getToken();
        AccountDto account = accountService.getAccount(jwtUtils.getUsername(token));

        invalidTokenService.invalidate(account, token);
        return jwtUtils.getEmptyToken();
    }
}
