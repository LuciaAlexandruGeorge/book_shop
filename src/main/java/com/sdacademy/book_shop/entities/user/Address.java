package com.sdacademy.book_shop.entities.user;

import com.sdacademy.book_shop.entities.cartNoder.Orders;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String city;
    String street;
    int streetNumber;
    String block;
    String ap;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "user_addresses",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    User user;

    @OneToMany(mappedBy = "address")
    List<Orders> ordersList;

}
