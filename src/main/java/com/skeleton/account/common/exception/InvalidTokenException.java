package com.skeleton.account.common.exception;

public class InvalidTokenException extends Exception {
    public static final String TOKEN_IS_ALREADY_INVALID = "Token is already invalid";

    public InvalidTokenException() {
        super();
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
