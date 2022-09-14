package com.sdacademy.book_shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderBookDto {
    long productId;
    int productQuantity;
}
