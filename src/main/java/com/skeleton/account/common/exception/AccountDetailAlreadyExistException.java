package com.skeleton.account.common.exception;

public class AccountDetailAlreadyExistException extends Exception {

    public AccountDetailAlreadyExistException() {
        super();
    }

    public AccountDetailAlreadyExistException(String message) {
        super(message);
    }
}
