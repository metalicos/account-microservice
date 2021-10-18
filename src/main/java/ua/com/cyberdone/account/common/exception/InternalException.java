package ua.com.cyberdone.account.common.exception;

public class InternalException extends RuntimeException {

    public InternalException(Exception e) {
        super(e);
    }
}
