package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.BookDto;
import com.sdacademy.book_shop.dto.UserDto;
import com.sdacademy.book_shop.entities.book.Book;
import com.sdacademy.book_shop.entities.book.BookCategory;
import com.sdacademy.book_shop.entities.user.User;
import com.sdacademy.book_shop.exceptions.EntityNotFoundError;
import com.sdacademy.book_shop.exceptions.SdException;
import com.sdacademy.book_shop.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class BookService {

    static final Logger log = LoggerFactory.getLogger(BookService.class);
    BookRepository bookRepository;
    ModelMapper modelMapper;
    BookMapper bookMapper;

    private List<BookDto> getBookDtos(final List<Book> books) {
        return books.stream()
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect((toList()));
    }

    public List<BookDto> getByTitleIgnoreCase(String title){
        return bookRepository.findAllByTitleIgnoreCase(title).stream().map(book -> bookMapper.convertToDto(book)).collect(Collectors.toList());
        }

    public List<BookDto> getAllByAuthor_FirstNameOrLastName(String name){
        return bookRepository.findAllByAuthor_FirstNameOrLastName(name,name).stream().map(book -> bookMapper.convertToDto(book)).collect(Collectors.toList());

    }

    public List<BookDto> getAllByCategory(BookCategory category){
        return bookRepository.findAllByBookCategory(category).stream().map(book->bookMapper.convertToDto(book)).collect(Collectors.toList());
    }

    public List<BookDto> getAllBooks() {

        return bookRepository.findAll().stream().map(book -> bookMapper.convertToDto(book)).collect(Collectors.toList());
    }

    public BookDto createBook(BookDto form) {
        Book book = bookMapper.convertToEntity(form);
        book=bookRepository.save(book);
        return bookMapper.convertToDto(book);
    }

    public void update(Long bookId, BookDto bookDto) {
        log.info("update book {0}", bookDto);

        bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        createBook(bookDto);
    }



    public BookDto findById(long id) {
        Book entityBook = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundError(String.format("Book with %d does not exist", id)));
        return bookMapper.convertToDto(entityBook);
    }

    public void deleteById(long id) {
        bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundError(String.format("Book with %s does not exist", id)));
        bookRepository.deleteById(id);
    }


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

    public List<BookDto> getAllByPriceBetween(final int min, final int max) {
        return getBookDtos((bookRepository.findAllByPriceBetween(min, max)));
    }

    public List<BookDto> getWherePriceIsGreaterThanX(Integer min) {
        return getBookDtos(bookRepository.findWherePriceIsGreaterThanX(min));
    }

}
