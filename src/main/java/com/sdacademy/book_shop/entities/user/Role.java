package com.sdacademy.book_shop.entities.user;

import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "role")
@FieldDefaults(level = PRIVATE)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @OneToMany
    List<User> userList;
    RoleType roleType;


}
