package com.sdacademy.book_shop.dto;

import com.sdacademy.book_shop.entities.book.BookCategory;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class BookDto {
    Long id;
    BookCategory bookCategory;
    String title;
    Long authorId;
    String ISBN;
    int pagesNum;
    double price;
    String description;
    String thumbnail;
    int quantity;
    AuthorDto authorDto;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(id, bookDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
