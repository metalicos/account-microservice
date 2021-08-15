package com.skeleton.account.dto;

import com.skeleton.account.common.constant.enumerations.Gender;
import com.skeleton.account.entity.Account;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class AccountDetailDto {
    private Long id;
    private LocalDateTime createdTimestamp;
    private String firstName;
    private String lastName;
    private String patronymic;
    private LocalDate birthDate;
    private Gender gender;
    private Byte[] photo;
    private Account account;
}
