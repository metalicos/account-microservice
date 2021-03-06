package ua.com.cyberdone.account.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ua.com.cyberdone.account.common.constant.Regex;

import javax.validation.constraints.Pattern;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChangeEmailDto {

    @Pattern(regexp = Regex.EMAIL,
            message = Regex.EMAIL_FAIL_MESSAGE)
    private String oldEmail;

    @Pattern(regexp = Regex.EMAIL,
            message = Regex.EMAIL_FAIL_MESSAGE)
    private String newEmail;
}
