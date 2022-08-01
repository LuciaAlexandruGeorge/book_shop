package com.sdacademy.book_shop.repository;

import com.sdacademy.book_shop.entities.book.Book;
import com.sdacademy.book_shop.entities.book.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByAuthor_FirstNameOrLastName(String authorFirstName, String authorLastName);

    List<Book> findAllByTitleIgnoreCase(String title);

    List<Book> findAllByTitle(String title);

    List<Book> findAllByBookCategory(BookCategory bookCategory);

    Optional<Book> findByISBN(String ISBN);

    Optional<Book> findByAuthorAndISBN(String author, String ISBN);

    List<Book> findTop3ByAuthorOrderByPagesNumDesc(String author);

    List<Book> findByTitleStartingWith(String title);

    List<Book> findAllByPagesNumBetween(int min, int max);

    @Query("SELECT book FROM books book WHERE book.pagesNum > :min ")
    List<Book> findWherePagesNumIsGreaterThanX(@Param(value = "min") Integer min);

    List<Book> findAllByPriceBetween(int min, int max);

    @Query("SELECT book FROM books book WHERE book.price > :min ")
    List<Book> findWherePriceIsGreaterThanX(@Param(value = "min") Integer min);

}
