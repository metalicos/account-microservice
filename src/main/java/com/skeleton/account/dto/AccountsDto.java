package com.skeleton.account.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountsDto {
    private List<AccountDto> accounts;
}
