package ua.com.cyberdone.account.mapper;

import org.modelmapper.ModelMapper;
import ua.com.cyberdone.account.entity.Account;

public class AccountMapper<Dto> extends AbstractMapper<Account, Dto> {
    public AccountMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
