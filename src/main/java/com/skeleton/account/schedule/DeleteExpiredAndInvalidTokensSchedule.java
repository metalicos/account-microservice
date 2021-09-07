package com.skeleton.account.schedule;

import com.skeleton.account.repository.InvalidTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class DeleteExpiredAndInvalidTokensSchedule {

    private final InvalidTokenRepository invalidTokenRepository;

    /**
     * Delete old tokens that became already expired.
     * After: 15 minutes init. Every: 15 minutes.
     */
    @Transactional
    @Scheduled(initialDelay = 900000, fixedDelay = 900000)
    public void deleteExpiredAndInvalidTokens() {
        invalidTokenRepository.deleteAllByExpirationDateBefore(new Date());
    }
}
