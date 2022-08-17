package com.sdacademy.book_shop.repository;

import com.sdacademy.book_shop.entities.book.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
    List<BookCategory> findAll();

    Optional<BookCategory> findByNameIgnoreCase(String name);

}
