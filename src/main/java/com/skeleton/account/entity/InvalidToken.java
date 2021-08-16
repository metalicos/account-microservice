package com.skeleton.account.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "invalid_tokens")
@EqualsAndHashCode(callSuper = true)
public class InvalidToken extends BasicEntity {

    @OneToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "invalid_token", nullable = false)
    private String invalidToken;

    @Column(name = "expiration_date", nullable = false)
    private Date expirationDate;
}
