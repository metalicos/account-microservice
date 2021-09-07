package com.skeleton.account.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Pattern;

import static com.skeleton.account.common.constant.Regex.TOKEN_FAIL_MESSAGE;
import static com.skeleton.account.common.constant.Regex.TOKEN_WITH_TYPE;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogoutDto {
    @Pattern(regexp = TOKEN_WITH_TYPE,
            message = TOKEN_FAIL_MESSAGE)
    private String token;
}
