package com.skeleton.account.repository;

import com.skeleton.account.entity.Account;
import com.skeleton.account.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {

    boolean existsByAccountAndInvalidToken(Account account, String invalidToken);
}
