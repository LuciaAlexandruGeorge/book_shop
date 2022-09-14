package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.OrderDto;
import com.sdacademy.book_shop.entities.cartNoder.OrderCommand;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OrderMapper implements Mapper<OrderCommand, OrderDto>  {
    UserMapper userMapper;
    OrderLineMapper orderLineMapper;
    @Override
    public OrderDto convertToDto(OrderCommand entity){
        OrderDto result = new OrderDto();
        result.setId((entity.getId()));
        result.setTotalPrice(entity.getTotalPrice());
        result.setAddress(entity.getAddress());
        result.setOrderDate(entity.getOrderDate());
        if(entity.getEntries()!=null)
        result.setEntries(entity.getEntries().stream().map(e->orderLineMapper.convertToDto(e)).collect(Collectors.toList()));
        if (entity.getUser() !=null) {
            result.setUser(userMapper.convertToDto(entity.getUser()));
        }
        result.setOrderStatus(entity.getOrderStatus());
        return result;
    }

    @Override
    public OrderCommand convertToEntity(OrderDto dto){
        OrderCommand result = new OrderCommand();
        result.setId((dto.getId()));
        result.setTotalPrice(dto.getTotalPrice());
        result.setAddress(dto.getAddress());
        result.setOrderDate(dto.getOrderDate());
        if (dto.getEntries() !=null) {
            result.setEntries(dto.getEntries().stream().map(ol->orderLineMapper.convertToEntity(ol)).collect(Collectors.toList()));
        }
        if (dto.getUser() != null) {
            result.setUser(userMapper.convertToEntity(dto.getUser()));
        }
        result.setOrderStatus(dto.getOrderStatus());
        return result;
    }
}
