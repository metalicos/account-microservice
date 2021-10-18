package ua.com.cyberdone.account.service;

import ua.com.cyberdone.account.common.exception.AlreadyExistException;
import ua.com.cyberdone.account.config.security.JwtService;
import ua.com.cyberdone.account.dto.account.AccountDto;
import ua.com.cyberdone.account.entity.Account;
import ua.com.cyberdone.account.entity.InvalidToken;
import ua.com.cyberdone.account.mapper.AccountMapper;
import ua.com.cyberdone.account.repository.InvalidTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvalidTokenService {
    private final InvalidTokenRepository invalidTokenRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public void invalidate(AccountDto accountDto, String token) throws AlreadyExistException {
        if (jwtService.isValidToken(token)) {
            saveInvalidTokenIfNotExist(accountDto, token);
        }
    }

    private void saveInvalidTokenIfNotExist(AccountDto accountDto, String token) throws AlreadyExistException {
        var account = new AccountMapper<AccountDto>(modelMapper).toEntity(accountDto, Account.class);
        if (invalidTokenRepository.existsByAccountAndInvalidToken(account, token)) {
            throw new AlreadyExistException("You have already logged out");
        }
        invalidTokenRepository.save(InvalidToken.builder()
                .account(account)
                .invalidToken(token)
                .expirationDate(jwtService.getExpirationDate(token))
                .build());
        SecurityContextHolder.clearContext();
    }
}
