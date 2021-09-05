package com.skeleton.account.controller;

import com.skeleton.account.common.exception.AccountUpdatingException;
import com.skeleton.account.dto.ChangeFullNameDto;
import com.skeleton.account.dto.ChangePasswordDto;
import com.skeleton.account.dto.TokenDto;
import com.skeleton.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/change")
public class AccountChangesController {

    public static final String OK = "OK";
    private final AccountService accountService;

    @PostMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto)
            throws AccountUpdatingException {
        accountService.changePassword(changePasswordDto);
        return ResponseEntity.ok(OK);
    }

    @PostMapping("/fullname")
    public ResponseEntity<String> changeFullName(@RequestBody @Valid ChangeFullNameDto changeFullNameDto)
            throws AccountUpdatingException {
        accountService.changeFullName(changeFullNameDto);
        return ResponseEntity.ok(OK);
    }
}
