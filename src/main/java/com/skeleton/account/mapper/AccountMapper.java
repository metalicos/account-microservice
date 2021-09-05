package com.skeleton.account.mapper;

import com.skeleton.account.entity.Account;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class AccountMapper<Dto> {

    private final ModelMapper modelMapper;

    public Account toEntity(Dto dto) {
        return modelMapper.map(dto, Account.class);
    }

    public Dto toDto(Account account, Class<Dto> clazz) {
        return modelMapper.map(account, clazz);
    }

    public List<Dto> toDtoList(List<Account> accounts, Class<Dto> clazz) {
        if (isNull(accounts) || accounts.isEmpty()) {
            return Collections.emptyList();
        }
        return accounts.stream().map(entity -> toDto(entity, clazz))
                .collect(toList());
    }

    public List<Account> toEntityList(List<Dto> dtos) {
        if (isNull(dtos) || dtos.isEmpty()) {
            return Collections.emptyList();
        }
        return dtos.stream().map(this::toEntity).collect(toList());
    }

}
