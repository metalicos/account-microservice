package com.skeleton.account.repository;

import com.skeleton.account.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {

    @Query("select detail " +
            "from AccountDetail detail " +
            "where detail.account.username = :username")
    Optional<AccountDetail> findByAccountUsername(@Param("username") String username);

    @Query("delete from AccountDetail detail " +
            "where detail.account.username = :username")
    Optional<AccountDetail> deleteByAccountUsername(@Param("username") String username);

    @Query("select case when count(detail) > 0 then true else false end " +
            "from AccountDetail detail " +
            "where detail.account.username = :username")
    boolean existsByAccountUsername(@Param("username") String username);

    @Modifying
    @Query("update AccountDetail detail set " +
            "detail.firstName = :firstName, " +
            "detail.lastName = :lastName, " +
            "detail.patronymic = :patronymic " +
            "where detail.account.username = :username")
    void updateFullName(@Param("username") String username,
                        @Param("firstName") String firstName,
                        @Param("lastName") String lastName,
                        @Param("patronymic") String patronymic);

    @Modifying
    @Query("update AccountDetail detail set detail.photo = :photo " +
            "where detail.account.username = :username")
    void updatePhoto(@Param("username") String username,
                     @Param("photo") String photo);
}
