package com.skeleton.account.common.exception;

public class AlreadyExistException extends Exception {
    public AlreadyExistException() {
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
