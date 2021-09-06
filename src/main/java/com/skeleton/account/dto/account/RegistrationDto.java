package com.skeleton.account.dto.account;

import com.skeleton.account.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class RegistrationDto {
    private String creatorToken;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Set<Role> roles;
}
