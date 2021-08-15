package com.skeleton.account.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;

import static com.skeleton.account.common.constant.Regex.TOKEN;
import static com.skeleton.account.common.constant.Regex.TOKEN_FAIL_MESSAGE;
import static com.skeleton.account.common.constant.Regex.TOKEN_TYPE;
import static com.skeleton.account.common.constant.Regex.TOKEN_TYPE_FAIL_MESSAGE;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class LogoutDto {
    @Pattern(regexp = TOKEN,
            message = TOKEN_FAIL_MESSAGE)
    private String token;

    @Pattern(regexp = TOKEN_TYPE,
            message = TOKEN_TYPE_FAIL_MESSAGE)
    private String tokenType;
}
