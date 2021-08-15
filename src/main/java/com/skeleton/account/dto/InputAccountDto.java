package com.skeleton.account.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InputAccountDto {
    private String username;
    private String password;
}
