package ua.com.cyberdone.account.mapper;

import ua.com.cyberdone.account.entity.Account;
import org.modelmapper.ModelMapper;

public class AccountMapper<Dto> extends AbstractMapper<Account, Dto> {
    public AccountMapper(ModelMapper modelMapper) {
        super(modelMapper);
    }
}
