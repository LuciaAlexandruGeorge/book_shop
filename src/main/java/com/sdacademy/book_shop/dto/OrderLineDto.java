package com.sdacademy.book_shop.dto;

import com.sdacademy.book_shop.entities.book.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class OrderLineDto {
    Integer id;
    int quantity;
    BookDto bookDto;
    double price;
    OrderDto order;
}
