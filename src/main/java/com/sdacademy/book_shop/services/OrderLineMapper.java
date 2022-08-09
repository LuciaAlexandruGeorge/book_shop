package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.OrderLineDto;
import com.sdacademy.book_shop.entities.cartNoder.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper implements Mapper<OrderLine, OrderLineDto> {
    @Override
    public OrderLineDto convertToDto(OrderLine entity) {
        return null;
    }

    @Override
    public OrderLine convertToEntity(OrderLineDto dto) {
        return null;
    }
}
