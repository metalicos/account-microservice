package com.skeleton.account.service;

import com.skeleton.account.common.config.security.JwtService;
import com.skeleton.account.common.exception.AccountCreationException;
import com.skeleton.account.common.exception.AccountReadException;
import com.skeleton.account.common.exception.AccountUpdatingException;
import com.skeleton.account.dto.AccountDto;
import com.skeleton.account.dto.AccountsDto;
import com.skeleton.account.dto.AddAccountDto;
import com.skeleton.account.dto.ChangeFullNameDto;
import com.skeleton.account.dto.ChangePasswordDto;
import com.skeleton.account.entity.Account;
import com.skeleton.account.mapper.AccountMapper;
import com.skeleton.account.repository.AccountRepository;
import com.skeleton.account.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.skeleton.account.common.util.AccountUtils.DEFAULT_ROLE;
import static com.skeleton.account.common.util.AccountUtils.permittedToCreateNewUser;
import static com.skeleton.account.common.util.AccountUtils.setupAccount;
import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public AccountsDto getAllAccounts() throws AccountReadException {
        var accounts = Optional.of(accountRepository.findAll())
                .orElseThrow(() -> new AccountReadException("None accounts was found."));
        var mapper = new AccountMapper<AccountDto>(modelMapper);
        return AccountsDto.builder().accounts(mapper.toDtoList(accounts, AccountDto.class)).build();
    }

    public AccountsDto getAllAccounts(int page, int size) throws AccountReadException {
        var accounts = Optional.of(accountRepository.findAll(PageRequest.of(page, size)))
                .orElseThrow(() -> new AccountReadException("None accounts was found."));
        var mapper = new AccountMapper<AccountDto>(modelMapper);
        return AccountsDto.builder()
                .page(page)
                .elementsOnThePage(size)
                .foundElements(accounts.getNumberOfElements())
                .totallyElements(accounts.getTotalElements())
                .totallyPages(accounts.getTotalPages())
                .accounts(mapper.toDtoList(accounts.getContent(), AccountDto.class))
                .build();
    }

    public AccountsDto getAllAccounts(int page, int size, String direction, String sortBy)
            throws AccountReadException {
        var sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        var accounts = Optional.of(accountRepository.findAll(PageRequest.of(page, size, sort)))
                .orElseThrow(() -> new AccountReadException("None accounts was found."));
        var mapper = new AccountMapper<AccountDto>(modelMapper);
        return AccountsDto.builder()
                .page(page)
                .elementsOnThePage(size)
                .foundElements(accounts.getNumberOfElements())
                .totallyElements(accounts.getTotalElements())
                .totallyPages(accounts.getTotalPages())
                .sortedBy(sortBy)
                .sortDirection(direction)
                .accounts(mapper.toDtoList(accounts.getContent(), AccountDto.class))
                .build();
    }

    public AccountDto getAccount(String username) throws AccountReadException {
        var account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AccountReadException("Account not found."));
        var mapper = new AccountMapper<AccountDto>(modelMapper);
        return mapper.toDto(account, AccountDto.class);
    }

    public AccountDto addAccount(AddAccountDto addAccountDto) throws AccountCreationException {
        if (!accountRepository.existsByUsername(addAccountDto.getUsername())) {
            var mapper = new AccountMapper<AddAccountDto>(modelMapper);
            var account = mapper.toEntity(addAccountDto);
            var token = addAccountDto.getCreatorToken();
            if (nonNull(token)) {
                var creatorUserUsername = jwtService.getUsername(token);
                var creatorAccount = accountRepository.findByUsername(creatorUserUsername).orElseThrow(
                        () -> new AccountCreationException("Account creator is not found."));
                var permitted = permittedToCreateNewUser(creatorAccount);
                return createNewUserIfCreatorPermitted(permitted, account);
            }
            var defaultRole = Set.of(roleRepository.findByRole(DEFAULT_ROLE).orElseThrow());
            setupAccount(passwordEncoder, account, defaultRole);
            return createNewUser(account);
        }
        throw new AccountCreationException("Account with username=" + addAccountDto.getUsername() + " exists.");
    }

    private AccountDto createNewUserIfCreatorPermitted(boolean permitted, Account account)
            throws AccountCreationException {
        if (permitted) {
            setupAccount(passwordEncoder, account);
            return createNewUser(account);
        }
        throw new AccountCreationException("Account creator is not permitted to create new User");
    }

    private AccountDto createNewUser(Account account) {
        var savedAccount = accountRepository.save(account);
        var accountMapper = new AccountMapper<AccountDto>(modelMapper);
        var accountDto = accountMapper.toDto(savedAccount, AccountDto.class);
        log.info("Account={} is successfully added", accountDto);
        return accountDto;
    }

    public void updateAccountPassword(String username, String password) throws AccountUpdatingException {
        var account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AccountUpdatingException("Account not found."));
        account.setPassword(passwordEncoder.encode(password));
        accountRepository.save(account);
        log.info("Account password is changed");
    }

    public void updateAccountUsername(String username, String newUsername) throws AccountUpdatingException {
        if (accountRepository.existsByUsername(newUsername)) {
            throw new AccountUpdatingException("Account with this username exists, choose another one.");
        }
        var account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AccountUpdatingException("Account not found."));
        account.setUsername(newUsername);
        accountRepository.save(account);
        log.info("Account username is changed from '{}' to '{}'", username, newUsername);
    }

    public void deleteAccount(String username) {
        accountRepository.deleteByUsername(username);
        log.info("Account with 'username'='{}' is deleted", username);
    }

    public void deleteAllAccounts() {
        accountRepository.deleteAll();
        log.info("All Accounts are deleted");
    }

    public void changePassword(ChangePasswordDto dto) throws AccountUpdatingException {
        var account = accountRepository.findByUsername(dto.getUsername()).orElseThrow(
                () -> new AccountUpdatingException("Account not found"));
        account.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        accountRepository.save(account);
        log.info("Password is updated");
    }

    public void changeFullName(ChangeFullNameDto dto) throws AccountUpdatingException {
        var account = accountRepository.findByUsername(dto.getUsername()).orElseThrow(
                () -> new AccountUpdatingException("Account not found"));
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setPatronymic(dto.getPatronymic());
        accountRepository.save(account);
        log.info("Full name is updated");
    }
}
