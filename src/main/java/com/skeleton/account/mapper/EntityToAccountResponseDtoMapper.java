package com.skeleton.account.mapper;

import com.skeleton.account.dto.AccountResponseDto;
import com.skeleton.account.entity.Account;
import com.skeleton.account.mapper.abstraction.AbstractEntityDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EntityToAccountResponseDtoMapper extends AbstractEntityDtoConverter<Account, AccountResponseDto> {

    public EntityToAccountResponseDtoMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
