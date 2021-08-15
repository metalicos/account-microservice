package com.skeleton.account.service;

import com.skeleton.account.common.exception.AccountAlreadyExistException;
import com.skeleton.account.common.exception.AccountNotFoundException;
import com.skeleton.account.common.util.SetupAccountUtil;
import com.skeleton.account.dto.AccountDto;
import com.skeleton.account.dto.AccountsDto;
import com.skeleton.account.dto.InputAccountDto;
import com.skeleton.account.entity.Account;
import com.skeleton.account.mapper.EntityDtoConverter;
import com.skeleton.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.skeleton.account.common.constant.enumerations.StaticRoles.USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final EntityDtoConverter converter;

    public AccountsDto getAllAccounts()
            throws AccountNotFoundException {

        List<Account> accounts =
                Optional.of(accountRepository.findAll())
                        .orElseThrow(() ->
                                new AccountNotFoundException("None accounts was found."));
        converter.setTypeMap(Account.class, AccountDto.class);
        return AccountsDto.builder()
                .accounts(converter.toDtoList(accounts))
                .build();
    }

    public AccountDto getAccount(String username)
            throws AccountNotFoundException {

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found."));
        converter.setTypeMap(Account.class, AccountDto.class);
        return converter.toDto(account);
    }

    public AccountDto addAccount(InputAccountDto inputAccountDto)
            throws AccountAlreadyExistException {

        boolean exists = accountRepository
                .existsByUsername(inputAccountDto.getUsername());
        if (!exists) {
            converter.setTypeMap(Account.class, InputAccountDto.class);
            Account account = converter.toEntity(inputAccountDto);
            SetupAccountUtil.setupAccount(account, USER);
            Account savedAccount = accountRepository.save(account);

            converter.setTypeMap(Account.class, AccountDto.class);
            AccountDto accountDto = converter.toDto(savedAccount);
            log.info("Account={} is successfully added", accountDto);
            return accountDto;
        }
        throw new AccountAlreadyExistException(
                String.format("Account with 'username'='%s' already exist.", inputAccountDto.getUsername()));
    }

    public void updateAccountPassword(String username, String password)
            throws AccountNotFoundException {

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found."));

        account.setPassword(password);
        accountRepository.save(account);
        log.info("Account password is changed");
    }

    public void updateAccountUsername(String username, String newUsername)
            throws AccountAlreadyExistException, AccountNotFoundException {

        if (accountRepository.existsByUsername(newUsername)) {
            throw new AccountAlreadyExistException("Account with this username exists, choose another one.");
        }

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found."));
        account.setUsername(newUsername);
        accountRepository.save(account);
        log.info("Account username is changed from '{}' to '{}'", username, newUsername);
    }

    public void deleteAccount(InputAccountDto inputAccountDto) {
        accountRepository.deleteByUsername(inputAccountDto.getUsername());
        log.info("Account with 'username'='{}' is deleted", inputAccountDto.getUsername());
    }

    public void deleteAllAccounts() {
        accountRepository.deleteAll();
        log.info("All Accounts are deleted");
    }
}
