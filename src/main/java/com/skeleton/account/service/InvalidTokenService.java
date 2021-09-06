package com.skeleton.account.service;

import com.skeleton.account.common.exception.AlreadyExistException;
import com.skeleton.account.config.security.JwtService;
import com.skeleton.account.dto.account.AccountDto;
import com.skeleton.account.entity.Account;
import com.skeleton.account.entity.InvalidToken;
import com.skeleton.account.mapper.AccountMapper;
import com.skeleton.account.repository.InvalidTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
    }
}
