package com.skeleton.account.service;

import com.skeleton.account.common.config.security.JwtService;
import com.skeleton.account.dto.AccountDto;
import com.skeleton.account.entity.Account;
import com.skeleton.account.entity.InvalidToken;
import com.skeleton.account.mapper.AccountMapper;
import com.skeleton.account.repository.InvalidTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvalidTokenService {
    private final InvalidTokenRepository invalidTokenRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public void invalidate(AccountDto accountDto, String token) {
        if (jwtService.isValidToken(token)) {
            saveInvalidToken(accountDto, token);
        }
    }

    private void saveInvalidToken(AccountDto accountDto, String token) {
        var accountMapper = new AccountMapper<AccountDto>(modelMapper);
        var account = accountMapper.toEntity(accountDto);
        if (!invalidTokenRepository.existsByAccountAndInvalidToken(account, token)) {
            var invalidToken = setupInvalidToken(account, token, jwtService.getExpirationDate(token));
            invalidTokenRepository.save(invalidToken);
        }
    }

    private InvalidToken setupInvalidToken(Account account, String token, Date expirationDate) {
        return InvalidToken.builder()
                .account(account)
                .invalidToken(token)
                .expirationDate(expirationDate)
                .build();
    }
}
