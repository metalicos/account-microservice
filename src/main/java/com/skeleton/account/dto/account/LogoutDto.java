package com.skeleton.account.dto.account;

import lombok.Data;

import javax.validation.constraints.Pattern;

import static com.skeleton.account.common.constant.Regex.TOKEN;
import static com.skeleton.account.common.constant.Regex.TOKEN_FAIL_MESSAGE;
import static com.skeleton.account.common.constant.Regex.TOKEN_TYPE;
import static com.skeleton.account.common.constant.Regex.TOKEN_TYPE_FAIL_MESSAGE;

@Data
public class LogoutDto {
    @Pattern(regexp = TOKEN,
            message = TOKEN_FAIL_MESSAGE)
    private String token;

    @Pattern(regexp = TOKEN_TYPE,
            message = TOKEN_TYPE_FAIL_MESSAGE)
    private String tokenType;
}
