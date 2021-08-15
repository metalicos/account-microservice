package com.skeleton.account.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "account_details")
@EqualsAndHashCode(callSuper = true)
public class AccountDetails extends BasicEntity {

}
