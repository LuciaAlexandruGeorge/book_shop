package com.sdacademy.book_shop.entities.book;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;


}
