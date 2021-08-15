package com.skeleton.account.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Pattern;

import static com.skeleton.account.common.constant.Regex.EMAIL;
import static com.skeleton.account.common.constant.Regex.EMAIL_FAIL_MESSAGE;
import static com.skeleton.account.common.constant.Regex.PASSWORD;
import static com.skeleton.account.common.constant.Regex.PASSWORD_FAIL_MESSAGE;

@Data
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Pattern(regexp = EMAIL,
            message = EMAIL_FAIL_MESSAGE)
    private String email;

    @Pattern(regexp = PASSWORD,
            message = PASSWORD_FAIL_MESSAGE)
    private String password;
}
