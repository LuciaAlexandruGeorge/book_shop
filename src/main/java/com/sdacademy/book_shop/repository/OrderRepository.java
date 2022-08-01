package com.sdacademy.book_shop.repository;

import com.sdacademy.book_shop.entities.cartNoder.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findAll();
}
