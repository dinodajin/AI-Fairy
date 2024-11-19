package com.LGDXSCHOOL._dx.exception;

public class UserAlreadyExistenceException extends RuntimeException {

    public UserAlreadyExistenceException() {
        super();
    }

    public UserAlreadyExistenceException(String message) {
        super(message);
    }

    public UserAlreadyExistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistenceException(Throwable cause) {
        super(cause);
    }

    protected UserAlreadyExistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
