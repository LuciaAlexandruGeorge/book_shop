package com.sdacademy.book_shop.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class BookDto {
    int id;
    @NotBlank(message = "Title must be provided")
    String title;
    @NotBlank(message = "Author must be provided")
    String author;
    @NotBlank(message = "ISBN must be provided")
    String ISBN;
    @NotNull(message = "Number of pages must be provided")
    @Positive(message = "Number of pages must be positive")
    int pagesNum;
}
