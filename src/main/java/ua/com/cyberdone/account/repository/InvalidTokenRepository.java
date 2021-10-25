package ua.com.cyberdone.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.com.cyberdone.account.entity.Account;
import ua.com.cyberdone.account.entity.InvalidToken;

import java.util.Date;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {

    void deleteAllByExpirationDateBefore(Date date);

    @Query("select count(token) > 0 from InvalidToken token " +
            "where token.account.username = :username and token.invalidToken = :token")
    boolean isInvalid(@Param("username") String username,
                      @Param("token") String invalidToken);

    boolean existsByAccountAndInvalidToken(Account account, String invalidToken);
}
