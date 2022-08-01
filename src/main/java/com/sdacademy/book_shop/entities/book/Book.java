package com.sdacademy.book_shop.entities.book;

import com.sdacademy.book_shop.entities.Author;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@Entity(name = "books")
@FieldDefaults(level = PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = AUTO)
    Long id;
    BookCategory bookCategory;
    String title;
    @ManyToOne
    Author author;
    String ISBN;
    int pagesNum;
    int price;
    private String description;
    private String thumbnail;
    private int quantity;

}
