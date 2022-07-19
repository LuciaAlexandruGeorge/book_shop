package com.sdacademy.book_shop.repository;

import com.sdacademy.book_shop.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByTitle(String title);

    Optional<Book> findByISBN(String ISBN);

    Optional<Book> findByAuthorAndISBN(String author, String ISBN);

    List<Book> findTop3ByAuthorOrderByPagesNumDesc(String author);

    List<Book> findByTitleStartingWith(String title);

    List<Book> findAllByPagesNumBetween(int min, int max);

    @Query("SELECT book FROM books book WHERE book.pagesNum > :min ")
    List<Book> findWherePagesNumIsGreaterThanX(@Param(value = "min") Integer min);
}
