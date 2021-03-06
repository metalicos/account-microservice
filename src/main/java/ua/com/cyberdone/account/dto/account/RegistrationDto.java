package ua.com.cyberdone.account.dto.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import ua.com.cyberdone.account.entity.Role;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationDto {
    private String creatorToken;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Set<Role> roles;
}
