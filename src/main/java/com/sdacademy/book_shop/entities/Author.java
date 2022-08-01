package com.sdacademy.book_shop.entities;

import com.sdacademy.book_shop.entities.book.Book;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Data
@FieldDefaults(level = PRIVATE)
public class Author {
    @Id
    @GeneratedValue
    Long id;
    String firstName;
    String lastName;
    @OneToMany(mappedBy = "author")
    List<Book> books;
}
