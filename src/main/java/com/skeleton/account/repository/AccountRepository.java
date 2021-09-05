package com.skeleton.account.repository;

import com.skeleton.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUsername(String username);

    void deleteByUsername(String username);

    boolean existsByUsername(String username);
}
