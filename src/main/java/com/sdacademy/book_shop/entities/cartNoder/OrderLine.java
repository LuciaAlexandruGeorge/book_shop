package com.sdacademy.book_shop.entities.cartNoder;

import com.sdacademy.book_shop.entities.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

import static lombok.AccessLevel.PRIVATE;

@Entity
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int quantity;

    @ManyToOne
     Book book;

    @ManyToOne
    @JoinTable(name = "order_orderlines",
            joinColumns = @JoinColumn(name = "orderline_id"),
            inverseJoinColumns = @JoinColumn(name = "orders_id"))
    OrderCommand orderCommand;

     double price;

    public OrderLine(int quantity, Book book, OrderCommand orderCommand, double price) {
        this.quantity = quantity;
        this.book = book;
        this.orderCommand = orderCommand;
        this.price = price;
    }
}