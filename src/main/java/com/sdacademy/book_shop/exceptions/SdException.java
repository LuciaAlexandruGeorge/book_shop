package com.sdacademy.book_shop.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
public class SdException extends RuntimeException {
    private final HttpStatus status;

    public SdException(final String message) {
        super(message);
        this.status = BAD_REQUEST;
    }
}
