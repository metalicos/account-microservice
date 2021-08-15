package com.skeleton.account.common.exception.messages;

public final class UsernameNotFoundMessage {

    private UsernameNotFoundMessage() {
    }

    public static String getMessage() {
        return "Username is not found";
    }

    public static String getMessage(Object username) {
        return String.format("Account with 'username'='%s' not found", username);
    }
}
