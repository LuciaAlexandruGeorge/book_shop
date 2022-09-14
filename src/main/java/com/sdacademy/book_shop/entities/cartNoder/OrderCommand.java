package com.sdacademy.book_shop.entities.cartNoder;

import com.sdacademy.book_shop.entities.user.Address;
import com.sdacademy.book_shop.entities.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class OrderCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    double totalPrice;

    @ManyToOne()
    @JoinTable(name ="order_addresses",
            joinColumns = @JoinColumn(name="order_id"),
            inverseJoinColumns = @JoinColumn(name="address_id") )
    Address address;

    Date orderDate;

    @OneToMany(mappedBy = "orderCommand")
    List<OrderLine> entries = new ArrayList<OrderLine>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "user_orders",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    User user;

    @Enumerated(EnumType.STRING)
    OrderStatus orderStatus;
}
