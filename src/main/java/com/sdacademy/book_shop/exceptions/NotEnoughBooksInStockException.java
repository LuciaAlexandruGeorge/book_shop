package com.sdacademy.book_shop.exceptions;

import com.sdacademy.book_shop.entities.book.Book;

public class NotEnoughBooksInStockException extends Exception {

    private static final String DEFAULT_MESSAGE = "Not enough books in stock";

    public NotEnoughBooksInStockException() {
        super(DEFAULT_MESSAGE);
    }

    public NotEnoughBooksInStockException(Book book) {
        super(String.format("Not enough %s books in stock. Only %d left", book.getTitle(), book.getQuantity()));
    }

}
