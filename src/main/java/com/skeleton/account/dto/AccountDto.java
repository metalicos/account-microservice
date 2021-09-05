package com.skeleton.account.dto;

import com.skeleton.account.entity.Role;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AccountDto {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Boolean isNonExpired;
    private Boolean isNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;
    private Byte[] photo;
    private Set<Role> roles;
}
