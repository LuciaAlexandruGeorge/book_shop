package com.sdacademy.book_shop.exceptions;

public final class DuplicateUserException extends SdException {
    private static final long serialVersionUID = -3335315696480992912L;

    public DuplicateUserException(final String message) {
        super(message);
    }
}
