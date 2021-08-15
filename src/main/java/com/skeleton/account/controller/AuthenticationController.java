package com.skeleton.account.controller;

import com.skeleton.account.dto.LoginDto;
import com.skeleton.account.dto.LogoutDto;
import com.skeleton.account.dto.TokenDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authorisation")
public class AuthenticationController {

    @PostMapping("/login")
    public TokenDto login(@RequestBody @Valid LoginDto loginDto) {
        return null;
    }

    @PostMapping("/logout")
    public TokenDto logout(@RequestBody @Valid LogoutDto logoutDto) {
        return null;
    }
}
