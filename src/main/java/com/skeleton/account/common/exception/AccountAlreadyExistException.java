package com.skeleton.account.common.exception;

public class AccountAlreadyExistException extends Exception {

    public AccountAlreadyExistException() {
        super();
    }

    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
