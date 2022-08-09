package com.sdacademy.book_shop.entities.user;


import com.sdacademy.book_shop.entities.cartNoder.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class User {
    @Id
    Long Id;
    @GeneratedValue(strategy = GenerationType.AUTO)
    String name;
    @OneToMany(mappedBy = "user")
    List<Order> orderList = new ArrayList<>();
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = false)
    String password;
   String address;
    String roles;
    String thumbnail;


}