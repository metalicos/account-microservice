package com.skeleton.account.controller;

import com.skeleton.account.common.exception.AccountNotFoundException;
import com.skeleton.account.common.exception.AuthenticationException;
import com.skeleton.account.common.exception.InvalidTokenException;
import com.skeleton.account.dto.LoginDto;
import com.skeleton.account.dto.LogoutDto;
import com.skeleton.account.dto.TokenDto;
import com.skeleton.account.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authorisation")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto loginDto)
            throws AuthenticationException {

        TokenDto tokenDto = authenticationService.login(loginDto);
        return ResponseEntity.ok(tokenDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<TokenDto> logout(@RequestBody @Valid LogoutDto logoutDto)
            throws InvalidTokenException, AuthenticationException, AccountNotFoundException {

        TokenDto tokenDto = authenticationService.logout(logoutDto);
        return ResponseEntity.ok(tokenDto);
    }
}
