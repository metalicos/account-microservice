package com.skeleton.account.dto.account;

import lombok.Data;

import javax.validation.constraints.Pattern;

import static com.skeleton.account.common.constant.Regex.EMAIL;
import static com.skeleton.account.common.constant.Regex.EMAIL_FAIL_MESSAGE;
import static com.skeleton.account.common.constant.Regex.PASSWORD;
import static com.skeleton.account.common.constant.Regex.PASSWORD_FAIL_MESSAGE;

@Data
public class LoginDto {
    @Pattern(regexp = EMAIL,
            message = EMAIL_FAIL_MESSAGE)
    private String email;

    @Pattern(regexp = PASSWORD,
            message = PASSWORD_FAIL_MESSAGE)
    private String password;
}
