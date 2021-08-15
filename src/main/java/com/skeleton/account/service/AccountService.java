package com.skeleton.account.service;

import com.skeleton.account.common.exception.AccountAlreadyExistException;
import com.skeleton.account.common.exception.AccountNotFoundException;
import com.skeleton.account.common.util.SetupAccountUtil;
import com.skeleton.account.dto.AccountRequestDto;
import com.skeleton.account.dto.AccountResponseDto;
import com.skeleton.account.dto.AccountsResponseDto;
import com.skeleton.account.entity.Account;
import com.skeleton.account.mapper.AccountRequestDtoToEntityMapper;
import com.skeleton.account.mapper.EntityToAccountResponseDtoMapper;
import com.skeleton.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.skeleton.account.common.constant.StaticRoles.USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final EntityToAccountResponseDtoMapper entityToAccountResponseDtoMapper;
    private final AccountRequestDtoToEntityMapper accountRequestDtoToEntityMapper;

    public AccountsResponseDto getAllAccounts()
            throws AccountNotFoundException {

        List<Account> accounts =
                Optional.of(accountRepository.findAll())
                        .orElseThrow(() ->
                                new AccountNotFoundException("None accounts was found."));
        return AccountsResponseDto.builder()
                .accounts(entityToAccountResponseDtoMapper.toDtoList(accounts))
                .build();
    }

    public AccountResponseDto getAccount(String username)
            throws AccountNotFoundException {

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account not found."));
        return entityToAccountResponseDtoMapper.toDto(account);
    }

    public AccountResponseDto addAccount(AccountRequestDto accountRequestDto)
            throws AccountAlreadyExistException {

        boolean exists = accountRepository
                .existsByUsername(accountRequestDto.getUsername());
        if (!exists) {
            Account account = accountRequestDtoToEntityMapper.toEntity(accountRequestDto);
            SetupAccountUtil.setupAccount(account, USER);
            Account savedAccount = accountRepository.save(account);

            AccountResponseDto accountResponseDto = entityToAccountResponseDtoMapper.toDto(savedAccount);
            log.info("Account={} is successfully added", accountResponseDto);
            return accountResponseDto;
        }
        throw new AccountAlreadyExistException(
                String.format("Account with 'username'='%s' already exist.", accountRequestDto.getUsername()));
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

    public void deleteAccount(AccountRequestDto accountRequestDto) {
        accountRepository.deleteByUsername(accountRequestDto.getUsername());
        log.info("Account with 'username'='{}' is deleted", accountRequestDto.getUsername());
    }

    public void deleteAllAccounts() {
        accountRepository.deleteAll();
        log.info("All Accounts are deleted");
    }
}
