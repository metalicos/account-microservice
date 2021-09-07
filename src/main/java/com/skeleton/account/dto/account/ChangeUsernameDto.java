package com.skeleton.account.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Pattern;

import static com.skeleton.account.common.constant.Regex.EMAIL;
import static com.skeleton.account.common.constant.Regex.EMAIL_FAIL_MESSAGE;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeUsernameDto {

    @Pattern(regexp = EMAIL,
            message = EMAIL_FAIL_MESSAGE)
    private String oldUsername;

    @Pattern(regexp = EMAIL,
            message = EMAIL_FAIL_MESSAGE)
    private String newUsername;
}
