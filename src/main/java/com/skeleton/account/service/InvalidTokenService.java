package com.skeleton.account.service;

import com.skeleton.account.common.config.security.JwtUtils;
import com.skeleton.account.common.exception.InvalidTokenException;
import com.skeleton.account.dto.AccountDto;
import com.skeleton.account.entity.Account;
import com.skeleton.account.entity.InvalidToken;
import com.skeleton.account.mapper.EntityDtoConverter;
import com.skeleton.account.repository.InvalidTokenRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.skeleton.account.common.exception.InvalidTokenException.TOKEN_IS_ALREADY_INVALID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvalidTokenService {


    private final InvalidTokenRepository invalidTokenRepository;
    private final EntityDtoConverter converter;
    private final JwtUtils jwtUtils;

    public void invalidate(AccountDto accountDto, String token) throws InvalidTokenException {
        try {
            saveInvalidToken(accountDto, token);
        } catch (ExpiredJwtException ex) {
            log.debug(TOKEN_IS_ALREADY_INVALID);
            throw new InvalidTokenException(TOKEN_IS_ALREADY_INVALID);
        }
    }

    private void saveInvalidToken(AccountDto accountDto, String token) {
        converter.setTypeMap(Account.class, AccountDto.class);
        Account account = converter.toEntity(accountDto);

        if (!existByAccountAndToken(account, token)) {
            Date expirationDate = jwtUtils.getExpirationDate(token);
            InvalidToken invalidToken = setupInvalidToken(account, token, expirationDate);
            invalidTokenRepository.save(invalidToken);
        }
    }

    public boolean existByAccountAndToken(Account account, String token) {
        return invalidTokenRepository.existsByAccountAndInvalidToken(account, token);
    }

    private InvalidToken setupInvalidToken(Account account, String token, Date expirationDate) {
        InvalidToken invalidToken = new InvalidToken();
        invalidToken.setAccount(account);
        invalidToken.setInvalidToken(token);
        invalidToken.setExpirationDate(expirationDate);
        return invalidToken;
    }

}
