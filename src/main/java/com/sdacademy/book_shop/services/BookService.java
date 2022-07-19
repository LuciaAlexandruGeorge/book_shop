package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.BookDto;
import com.sdacademy.book_shop.entities.Book;
import com.sdacademy.book_shop.exceptions.SdException;
import com.sdacademy.book_shop.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookService {
    BookRepository bookRepository;
    ModelMapper modelMapper;

    public List<BookDto> getAllByTitle(final String title) {
        return getBookDtos(bookRepository.findAllByTitle(title));
    }

    public BookDto getByISBN(final String ISBN) {
        val book = bookRepository.findByISBN(ISBN).orElseThrow(() -> new SdException(ISBN + " was not found"));
        return modelMapper.map(book, BookDto.class);
    }

    public BookDto getByAuthorAndISBN(final String author, final String ISBN) {
        val book = bookRepository.findByAuthorAndISBN(author, ISBN).orElseThrow(() -> new SdException(author + " with the following ISBN: " + ISBN + " was not found"));
        return modelMapper.map(book, BookDto.class);
    }

    public List<BookDto> getTop3ByAuthorOrderByPagesNumDesc(final String author) {
        return getBookDtos(bookRepository.findTop3ByAuthorOrderByPagesNumDesc(author));
    }

    public List<BookDto> getByTitleStartingWith(final String title) {
        return getBookDtos(bookRepository.findByTitleStartingWith(title));
    }

    public List<BookDto> getAllByPagesNumBetween(final int min, final int max) {
        return getBookDtos((bookRepository.findAllByPagesNumBetween(min, max)));
    }

    public List<BookDto> getWherePagesNumIsGreaterThanX(Integer min) {
        return getBookDtos(bookRepository.findWherePagesNumIsGreaterThanX(min));
    }

    private List<BookDto> getBookDtos(final List<Book> books) {
        return books.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect((toList()));
    }
}
