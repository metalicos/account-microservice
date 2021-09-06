package com.skeleton.account.dto.account;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

import static com.skeleton.account.common.constant.Regex.EMAIL;
import static com.skeleton.account.common.constant.Regex.EMAIL_FAIL_MESSAGE;
import static com.skeleton.account.common.constant.Regex.PASSWORD;
import static com.skeleton.account.common.constant.Regex.PASSWORD_FAIL_MESSAGE;

@Data
@Builder
public class ChangePasswordDto {
    @Pattern(regexp = EMAIL,
            message = EMAIL_FAIL_MESSAGE)
    private String username;

    @Pattern(regexp = PASSWORD,
            message = PASSWORD_FAIL_MESSAGE)
    private String newPassword;

    @Pattern(regexp = PASSWORD,
            message = PASSWORD_FAIL_MESSAGE)
    private String checkNewPassword;
}
