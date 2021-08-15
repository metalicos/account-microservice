package com.skeleton.account.service;

import com.skeleton.account.common.exception.AccountDetailAlreadyExistException;
import com.skeleton.account.common.exception.AccountDetailNotFoundException;
import com.skeleton.account.dto.AccountDetailDto;
import com.skeleton.account.dto.AccountDetailsDto;
import com.skeleton.account.entity.AccountDetail;
import com.skeleton.account.mapper.EntityDtoConverter;
import com.skeleton.account.repository.AccountDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountDetailService {

    private final AccountDetailRepository accountDetailRepository;
    private final EntityDtoConverter converter;

    public AccountDetailsDto getAllAccountDetails()
            throws AccountDetailNotFoundException {

        List<AccountDetail> details =
                Optional.of(accountDetailRepository.findAll())
                        .orElseThrow(() ->
                                new AccountDetailNotFoundException("None of account details were found."));
        converter.setTypeMap(AccountDetail.class, AccountDetailDto.class);
        return AccountDetailsDto.builder()
                .accountDetails(converter.toDtoList(details))
                .build();
    }

    public AccountDetailDto getAccountDetail(String username)
            throws AccountDetailNotFoundException {

        AccountDetail accountDetail = accountDetailRepository.findByAccountUsername(username)
                .orElseThrow(() ->
                        new AccountDetailNotFoundException("Account detail not found."));
        converter.setTypeMap(AccountDetail.class, AccountDetailDto.class);
        return converter.toDto(accountDetail);
    }

    public AccountDetailDto getAccountDetail(Long id)
            throws AccountDetailNotFoundException {

        AccountDetail accountDetail = accountDetailRepository.findById(id)
                .orElseThrow(() ->
                        new AccountDetailNotFoundException("Account detail not found."));
        converter.setTypeMap(AccountDetail.class, AccountDetailDto.class);
        return converter.toDto(accountDetail);
    }

    public AccountDetailDto addAccountDetail(AccountDetailDto detailDto)
            throws AccountDetailAlreadyExistException {

        boolean exists = accountDetailRepository
                .existsByAccountUsername(detailDto.getAccount().getUsername());
        if (!exists) {
            converter.setTypeMap(AccountDetail.class, AccountDetailDto.class);
            AccountDetail accountDetail = converter.toEntity(detailDto);
            AccountDetail savedAccountDetail = accountDetailRepository.save(accountDetail);

            AccountDetailDto accountDetailDto = converter.toDto(savedAccountDetail);
            log.info("AccountDetail={} is successfully added", accountDetailDto);
            return accountDetailDto;
        }
        throw new AccountDetailAlreadyExistException(
                String.format("AccountDetail for 'username'='%s' already exist.",
                        detailDto.getAccount().getUsername()));
    }

    public void updateFullNameDetail(String username, String firstName, String lastName, String patronymic) {

        accountDetailRepository.updateFullName(username, firstName, lastName, patronymic);
        log.info("AccountDetail for 'username'='{}' changed: Full name is ({} {} {})",
                username, firstName, lastName, patronymic);
    }

    public void updatePhotoDetail(String username, String photo) {

        accountDetailRepository.updatePhoto(username, photo);
        log.info("AccountDetail for 'username'='{}' changed: photo updated", username);
    }

    public void deleteAccountDetail(String username) {

        accountDetailRepository.deleteByAccountUsername(username);
        log.info("AccountDetail for 'username'='{}' are deleted", username);
    }

    public void deleteAccountDetail(Long id) {

        accountDetailRepository.deleteById(id);
        log.info("AccountDetail with 'id'='{}' is deleted", id);
    }

    public void deleteAllAccounts() {

        accountDetailRepository.deleteAll();
        log.info("All AccountDetails are deleted");
    }
}
