package com.skeleton.account.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "account_role")
@EqualsAndHashCode(callSuper = true)
public class Role extends BasicEntity {
    @Column(name = "role", length = 200, nullable = false, unique = true)
    private String role;
}
