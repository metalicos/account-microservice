package com.skeleton.account.entity;

import com.skeleton.account.common.constant.enumerations.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "account_detail")
@EqualsAndHashCode(callSuper = true)
public class AccountDetail extends BasicEntity {

    @Column(name = "firstName", length = 25, nullable = false)
    private String firstName;

    @Column(name = "lastName", length = 25, nullable = false)
    private String lastName;

    @Column(name = "patronymic", length = 25, nullable = false)
    private String patronymic;

    @Column(name = "birthDate")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "photo")
    private String photo;

    @OneToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id",
            nullable = false)
    private Account account;
}
