package com.sdacademy.book_shop.exceptions;

public class EntityNotFoundError extends RuntimeException{
    public EntityNotFoundError(String message) {
        super(message);
    }
}
