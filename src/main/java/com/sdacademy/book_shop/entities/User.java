package com.sdacademy.book_shop.entities;

import com.sdacademy.book_shop.enums.UserRol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    int id;
    @Column
    String username;
    @Column
    int age;
    @Column
    String city;
    @Column
    private UserRol rol;


}