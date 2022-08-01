package com.sdacademy.book_shop.dto;

import com.sdacademy.book_shop.entities.cartNoder.Orders;
import com.sdacademy.book_shop.entities.user.Address;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private List<Orders> orderList;
    private String password;
    private List<Address> addressList;
    private String thumbnail;
    private String roles;


}