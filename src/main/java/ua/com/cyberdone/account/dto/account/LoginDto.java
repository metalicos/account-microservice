package ua.com.cyberdone.account.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ua.com.cyberdone.account.common.constant.Regex;

import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginDto {
    @Pattern(regexp = Regex.EMAIL,
            message = Regex.EMAIL_FAIL_MESSAGE)
    private String email;

    @Pattern(regexp = Regex.PASSWORD,
            message = Regex.PASSWORD_FAIL_MESSAGE)
    private String password;
}
