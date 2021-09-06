package com.skeleton.account.service;

import com.skeleton.account.common.exception.AccessDeniedException;
import com.skeleton.account.common.exception.AlreadyExistException;
import com.skeleton.account.common.exception.NotFoundException;
import com.skeleton.account.config.security.JwtService;
import com.skeleton.account.dto.account.AccountDto;
import com.skeleton.account.dto.account.AccountsDto;
import com.skeleton.account.dto.account.ChangeFullNameDto;
import com.skeleton.account.dto.account.ChangePasswordDto;
import com.skeleton.account.dto.account.ChangeUsernameDto;
import com.skeleton.account.dto.account.RegistrationDto;
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

    public AccountsDto getAllAccounts() throws NotFoundException {
        var accounts = Optional.of(accountRepository.findAll())
                .orElseThrow(() -> new NotFoundException("None accounts was found."));
        return AccountsDto.builder()
                .accounts(new AccountMapper<AccountDto>(modelMapper).toDtoList(accounts, AccountDto.class)).build();
    }

    public AccountsDto getAllAccounts(int page, int size) throws NotFoundException {
        var accounts = Optional.of(accountRepository.findAll(PageRequest.of(page, size)))
                .orElseThrow(() -> new NotFoundException("None accounts was found."));
        return AccountsDto.builder()
                .page(page)
                .elementsOnThePage(size)
                .foundElements(accounts.getNumberOfElements())
                .totallyElements(accounts.getTotalElements())
                .totallyPages(accounts.getTotalPages())
                .accounts(new AccountMapper<AccountDto>(modelMapper).toDtoList(accounts.getContent(), AccountDto.class))
                .build();
    }

    public AccountsDto getAllAccounts(int page, int size, String direction, String sortBy) throws NotFoundException {
        var sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        var accounts = Optional.of(accountRepository.findAll(PageRequest.of(page, size, sort)))
                .orElseThrow(() -> new NotFoundException("None accounts was found."));
        return AccountsDto.builder()
                .page(page)
                .elementsOnThePage(size)
                .foundElements(accounts.getNumberOfElements())
                .totallyElements(accounts.getTotalElements())
                .totallyPages(accounts.getTotalPages())
                .sortedBy(sortBy)
                .sortDirection(direction)
                .accounts(new AccountMapper<AccountDto>(modelMapper).toDtoList(accounts.getContent(), AccountDto.class))
                .build();
    }

    public AccountDto getAccount(String username) throws NotFoundException {
        var account = accountRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException("Account not found."));
        return new AccountMapper<AccountDto>(modelMapper).toDto(account, AccountDto.class);
    }

    public AccountDto createAccount(RegistrationDto registrationDto)
            throws NotFoundException, AlreadyExistException, AccessDeniedException {
        if (!accountRepository.existsByUsername(registrationDto.getUsername())) {
            var account = new AccountMapper<RegistrationDto>(modelMapper).toEntity(registrationDto, Account.class);
            var token = registrationDto.getCreatorToken();
            if (nonNull(token)) {
                var creatorUserUsername = jwtService.getUsername(token);
                var creatorAccount = accountRepository.findByUsername(creatorUserUsername).orElseThrow(
                        () -> new NotFoundException("Account creator is not found."));
                var permitted = permittedToCreateNewUser(creatorAccount);
                return createNewUserIfCreatorPermitted(permitted, account);
            }
            var defaultRole = Set.of(roleRepository.findByRole(DEFAULT_ROLE).orElseThrow());
            setupAccount(passwordEncoder, account, defaultRole);
            return createNewUser(account);
        }
        throw new AlreadyExistException("Account with username=" + registrationDto.getUsername() + " exists.");
    }

    private AccountDto createNewUserIfCreatorPermitted(boolean permitted, Account account)
            throws AccessDeniedException {
        if (permitted) {
            setupAccount(passwordEncoder, account);
            return createNewUser(account);
        }
        throw new AccessDeniedException("Account creator is not permitted to create new User");
    }

    private AccountDto createNewUser(Account account) {
        var savedAccount = accountRepository.save(account);
        var accountDto = new AccountMapper<AccountDto>(modelMapper).toDto(savedAccount, AccountDto.class);
        log.info("Account={} is successfully added", accountDto);
        return accountDto;
    }

    public void deleteAccount(String username) {
        accountRepository.deleteByUsername(username);
        log.info("Account with 'username'='{}' is deleted", username);
    }

    public void deleteAllAccounts() {
        accountRepository.deleteAll();
        log.info("All Accounts are deleted");
    }

    public void changeAccountPassword(ChangePasswordDto dto) throws NotFoundException {
        var account = accountRepository.findByUsername(dto.getUsername()).orElseThrow(
                () -> new NotFoundException("Account not found"));
        account.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        accountRepository.save(account);
        log.info("Password is updated");
    }

    public void changeAccountFullName(ChangeFullNameDto dto) throws NotFoundException {
        var account = accountRepository.findByUsername(dto.getUsername()).orElseThrow(
                () -> new NotFoundException("Account not found"));
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setPatronymic(dto.getPatronymic());
        accountRepository.save(account);
        log.info("Full name is updated");
    }

    public void changeAccountUsername(ChangeUsernameDto dto)
            throws AlreadyExistException, NotFoundException {
        if (accountRepository.existsByUsername(dto.getNewUsername())) {
            throw new AlreadyExistException("Account with this username exists, choose another one.");
        }
        var account = accountRepository.findByUsername(dto.getOldUsername()).orElseThrow(
                () -> new NotFoundException("Account not found."));
        account.setUsername(dto.getNewUsername());
        accountRepository.save(account);
        log.info("Account username is changed from '{}' to '{}'", dto.getOldUsername(), dto.getNewUsername());
    }
}
