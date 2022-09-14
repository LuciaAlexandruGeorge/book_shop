package com.sdacademy.book_shop.dto;

import com.sdacademy.book_shop.entities.cartNoder.OrderCommand;
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
     List<OrderCommand> orderCommandList;
     String password;
     String address;
     String thumbnail;
     String roles;


}