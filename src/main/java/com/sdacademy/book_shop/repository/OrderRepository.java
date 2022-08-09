package com.sdacademy.book_shop.repository;

import com.sdacademy.book_shop.entities.cartNoder.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAll();
}
