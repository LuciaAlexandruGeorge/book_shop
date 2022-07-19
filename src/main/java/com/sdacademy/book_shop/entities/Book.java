package com.sdacademy.book_shop.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor
@Entity(name = "books")
@FieldDefaults(level = PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = AUTO)
    int id;
    @Column
    String title;
    @Column
    String author;
    @Column
    String ISBN;
    @Column
    int pagesNum;

}
