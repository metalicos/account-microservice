package com.skeleton.account.mapper;

import com.skeleton.account.entity.Account;
import org.modelmapper.ModelMapper;

public class AccountMapper<Dto> extends AbstractMapper<Account, Dto> {
    public AccountMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
