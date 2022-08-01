package com.sdacademy.book_shop.repository;

import com.sdacademy.book_shop.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
