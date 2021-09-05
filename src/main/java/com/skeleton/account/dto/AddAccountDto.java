package com.skeleton.account.dto;

import com.skeleton.account.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AddAccountDto {
    private String creatorToken;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Set<Role> roles;
}
