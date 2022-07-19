package com.sdacademy.book_shop.exceptions;

public final class UserDoesNotExistException extends SdException {
    private static final long serialVersionUID = -3335315696480992913L;

    public UserDoesNotExistException(final String message) {
        super(message);
    }
}