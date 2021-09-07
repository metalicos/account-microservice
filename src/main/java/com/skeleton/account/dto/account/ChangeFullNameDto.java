package com.skeleton.account.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Pattern;

import static com.skeleton.account.common.constant.Regex.EMAIL;
import static com.skeleton.account.common.constant.Regex.EMAIL_FAIL_MESSAGE;
import static com.skeleton.account.common.constant.Regex.FIRST_NAME;
import static com.skeleton.account.common.constant.Regex.FIRST_NAME_FAIL_MESSAGE;
import static com.skeleton.account.common.constant.Regex.LAST_NAME;
import static com.skeleton.account.common.constant.Regex.LAST_NAME_FAIL_MESSAGE;
import static com.skeleton.account.common.constant.Regex.PATRONYMIC;
import static com.skeleton.account.common.constant.Regex.PATRONYMIC_FAIL_MESSAGE;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeFullNameDto {
    @Pattern(regexp = EMAIL,
            message = EMAIL_FAIL_MESSAGE)
    private String username;

    @Pattern(regexp = FIRST_NAME,
            message = FIRST_NAME_FAIL_MESSAGE)
    private String firstName;

    @Pattern(regexp = LAST_NAME,
            message = LAST_NAME_FAIL_MESSAGE)
    private String lastName;

    @Pattern(regexp = PATRONYMIC,
            message = PATRONYMIC_FAIL_MESSAGE)
    private String patronymic;
}
