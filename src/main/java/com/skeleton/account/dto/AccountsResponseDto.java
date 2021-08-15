package com.skeleton.account.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountsResponseDto {
    private List<AccountResponseDto> accounts;
}
