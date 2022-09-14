package com.sdacademy.book_shop.repository;

import com.sdacademy.book_shop.entities.cartNoder.OrderCommand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderCommand, Long> {
    List<OrderCommand> findAll();
}
