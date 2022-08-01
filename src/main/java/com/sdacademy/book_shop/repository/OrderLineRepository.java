package com.sdacademy.book_shop.repository;

import com.sdacademy.book_shop.entities.cartNoder.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
