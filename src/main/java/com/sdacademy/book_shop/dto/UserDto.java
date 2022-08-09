package com.sdacademy.book_shop.dto;

import com.sdacademy.book_shop.entities.cartNoder.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.util.List;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class UserDto {

     Long id;
     String name;
     String email;
     List<Order> orderList;
     String password;
     String address;
     String thumbnail;
     String roles;


}