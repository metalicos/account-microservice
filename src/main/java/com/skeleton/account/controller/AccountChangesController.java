package com.skeleton.account.controller;

import com.skeleton.account.dto.ChangeFullNameDto;
import com.skeleton.account.dto.ChangePasswordDto;
import com.skeleton.account.dto.ChangeUsernameDto;
import com.skeleton.account.dto.TokenDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/change")
public class AccountChangesController {

    @PostMapping("/password")
    public TokenDto changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto) {
        return null;
    }

    @PostMapping("/username")
    public TokenDto changeUsername(@RequestBody @Valid ChangeUsernameDto changeUsernameDto) {
        return null;
    }

    @PostMapping("/fullname")
    public TokenDto changeUsername(@RequestBody @Valid ChangeFullNameDto changeFullNameDto) {
        return null;
    }
}
