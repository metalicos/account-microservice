package com.skeleton.account.mapper;

import com.skeleton.account.dto.AccountRequestDto;
import com.skeleton.account.entity.Account;
import com.skeleton.account.mapper.abstraction.AbstractEntityDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountRequestDtoToEntityMapper extends AbstractEntityDtoConverter<Account, AccountRequestDto> {

    public AccountRequestDtoToEntityMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
