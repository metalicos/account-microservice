package com.skeleton.account.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccountDetailsDto {
    private List<AccountDetailDto> accountDetails;
}
