package com.sdacademy.book_shop.controller;

import com.sdacademy.book_shop.dto.BookDto;
import com.sdacademy.book_shop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class BookController {
    static final String BASE_URL = "/api/books";
    private final BookService bookService;

    @GetMapping(path = "/title/{title}")
    public List<BookDto> getAllByTitle(@PathVariable String title) {
        return bookService.getAllByTitle(title);
    }

    @GetMapping(path = "/isbn/{isbn}")
    public BookDto getByISBN(@PathVariable(name = "isbn") String ISBN) {
        return bookService.getByISBN(ISBN);
    }

    @GetMapping
    public BookDto getByAuthorAndISBN(@RequestParam String author, @RequestParam(name = "isbn") String ISBN) {
        return bookService.getByAuthorAndISBN(author, ISBN);
    }

    @GetMapping(path = "/top/{author}")
    public List<BookDto> getTop3ByAuthorOrderByPagesNumDesc(@PathVariable String author) {
        return bookService.getTop3ByAuthorOrderByPagesNumDesc(author);
    }

    @GetMapping(path = "/starts-with/{title}")
    public List<BookDto> getByTitleStartingWith(@PathVariable String title) {
        return bookService.getByTitleStartingWith(title);
    }

    @GetMapping(path = "/range")
    public List<BookDto> getAllByPagesNumBetween(@RequestParam Integer min, @RequestParam(required = false) Integer max) {
        if (max == null) {
            return bookService.getWherePagesNumIsGreaterThanX(min);
        }
        return bookService.getAllByPagesNumBetween(min, max);
    }
}