package ua.com.cyberdone.account.common.exception.messages;

public final class AccountNotFoundMessage {

    private AccountNotFoundMessage() {
    }

    public static String getMessage() {
        return "Account is not found.";
    }

    public static String getMessage(Object object) {
        return String.format("Account with 'username'='%s' is not found.", object);
    }
}
