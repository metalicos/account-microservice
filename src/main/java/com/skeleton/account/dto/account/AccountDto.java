package com.skeleton.account.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.skeleton.account.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
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
    @JsonIgnore
    private Byte[] photo;
    private Set<Role> roles;
}
