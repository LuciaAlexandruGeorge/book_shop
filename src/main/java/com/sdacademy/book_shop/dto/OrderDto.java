package com.sdacademy.book_shop.dto;


import com.sdacademy.book_shop.entities.cartNoder.OrderStatus;
import com.sdacademy.book_shop.entities.user.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {
    Integer id;
    double totalPrice;
    Address address;
    Date orderDate;
    List<OrderLineDto> entries;
    UserDto user;
    OrderStatus orderStatus;
}
