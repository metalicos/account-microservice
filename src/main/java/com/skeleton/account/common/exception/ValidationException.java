package com.skeleton.account.common.exception;

public class ValidationException extends Exception{
    public ValidationException() {
    }

    public ValidationException(String message) {
        super(message);
    }
}
