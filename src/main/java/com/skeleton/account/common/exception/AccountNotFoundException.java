package com.skeleton.account.common.exception;

public class AccountNotFoundException extends Exception {

    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
