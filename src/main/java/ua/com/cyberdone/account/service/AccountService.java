package ua.com.cyberdone.account.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.cyberdone.account.common.exception.AccessDeniedException;
import ua.com.cyberdone.account.common.exception.AlreadyExistException;
import ua.com.cyberdone.account.common.exception.NotFoundException;
import ua.com.cyberdone.account.common.util.AccountUtils;
import ua.com.cyberdone.account.dto.account.AccountDto;
import ua.com.cyberdone.account.dto.account.AccountsDto;
import ua.com.cyberdone.account.dto.account.ChangeEmailDto;
import ua.com.cyberdone.account.dto.account.ChangeFullNameDto;
import ua.com.cyberdone.account.dto.account.ChangePasswordDto;
import ua.com.cyberdone.account.dto.account.RegistrationDto;
import ua.com.cyberdone.account.entity.Account;
import ua.com.cyberdone.account.mapper.AccountMapper;
import ua.com.cyberdone.account.repository.AccountRepository;
import ua.com.cyberdone.account.repository.RoleRepository;
import ua.com.cyberdone.account.security.JwtService;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

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

    @Transactional
    public AccountDto createAccount(RegistrationDto registrationDto)
            throws NotFoundException, AlreadyExistException, AccessDeniedException {
        if (!accountRepository.existsByUsername(registrationDto.getUsername())) {
            var account = new AccountMapper<RegistrationDto>(modelMapper).toEntity(registrationDto, Account.class);
            var token = registrationDto.getCreatorToken();
            if (nonNull(token)) {
                var creatorUserUsername = jwtService.getUsername(token);
                var creatorAccount = accountRepository.findByUsername(creatorUserUsername).orElseThrow(
                        () -> new NotFoundException("Account creator is not found."));
                var permitted = AccountUtils.permittedToCreateNewUser(creatorAccount);
                return createNewUserIfCreatorPermitted(permitted, account);
            }
            var defaultRole = Set.of(roleRepository.findByRole(AccountUtils.DEFAULT_ROLE).orElseThrow());
            AccountUtils.setupAccount(passwordEncoder, account, defaultRole);
            return createNewUser(account);
        }
        throw new AlreadyExistException("Account with username=" + registrationDto.getUsername() + " exists.");
    }

    private AccountDto createNewUserIfCreatorPermitted(boolean permitted, Account account)
            throws AccessDeniedException {
        if (permitted) {
            AccountUtils.setupAccount(passwordEncoder, account);
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

    @Transactional
    public void deleteAccount(String username) {
        accountRepository.deleteByUsername(username);
        log.info("Account with 'username'='{}' is deleted", username);
    }

    @Transactional
    public void deleteAllAccounts() {
        accountRepository.deleteAll();
        log.info("All Accounts are deleted");
    }

    @Transactional
    public void changeAccountPassword(ChangePasswordDto dto) throws NotFoundException {
        var account = accountRepository.findByUsername(dto.getUsername()).orElseThrow(
                () -> new NotFoundException("Account not found"));
        account.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        accountRepository.save(account);
        log.info("Password is updated");
    }

    @Transactional
    public void changeAccountFullName(ChangeFullNameDto dto) throws NotFoundException {
        var account = accountRepository.findByUsername(dto.getUsername()).orElseThrow(
                () -> new NotFoundException("Account not found"));
        account.setFirstName(dto.getFirstName());
        account.setLastName(dto.getLastName());
        account.setPatronymic(dto.getPatronymic());
        accountRepository.save(account);
        log.info("Full name is updated");
    }

    @Transactional
    public void changeAccountUsername(ChangeEmailDto dto)
            throws AlreadyExistException, NotFoundException {
        if (accountRepository.existsByUsername(dto.getOldEmail())) {
            throw new AlreadyExistException("Account with this username exists, choose another one.");
        }
        var account = accountRepository.findByUsername(dto.getOldEmail()).orElseThrow(
                () -> new NotFoundException("Account not found."));
        account.setUsername(dto.getNewEmail());
        accountRepository.save(account);
        log.info("Account username is changed from '{}' to '{}'", dto.getOldEmail(), dto.getNewEmail());
    }
}
