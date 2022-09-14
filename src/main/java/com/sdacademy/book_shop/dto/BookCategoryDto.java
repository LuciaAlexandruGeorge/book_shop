package com.sdacademy.book_shop.dto;

import com.sdacademy.book_shop.entities.book.Book;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class BookCategoryDto {
    Long id;
    String name;

}
