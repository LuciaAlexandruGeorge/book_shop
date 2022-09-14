package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.OrderLineDto;
import com.sdacademy.book_shop.entities.cartNoder.OrderLine;
import com.sdacademy.book_shop.repository.OrderLineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderLineService {
    @Autowired
    private OrderLineRepository orderLineRepository;
    @Autowired
    private OrderLineMapper orderLineMapper;
    @Autowired
    private BookMapper bookMapper;


    private static final Logger log = LoggerFactory.getLogger(OrderLineService.class);

    @Autowired
    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    // create
    public OrderLineDto save(OrderLineDto orderLine) {
        log.info("saving order line {}", orderLine.getId());
        OrderLine orderLineEntity = orderLineMapper.convertToEntity(orderLine);
        orderLineRepository.save(orderLineEntity);
        return orderLineMapper.convertToDto(orderLineEntity);
    }


    // find by id
    public OrderLine findById(Long id) {
        log.info("finding by id");
        return orderLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderLine not found"));
    }

    // update
    public void update(Long orderLineId, OrderLineDto orderLineDto) {
        log.info("update Order line {}", orderLineDto.getBookDto());

        orderLineRepository.findById(orderLineId)
                .map(existingOrderLine -> updateEntity(orderLineDto, existingOrderLine))
                .map(updatedOrderLine -> orderLineRepository.save(updatedOrderLine))
                .orElseThrow(() -> new RuntimeException("OrderLine not found"));
    }

    private OrderLine updateEntity(OrderLineDto orderLineDto, OrderLine existingOrderLine) {
        existingOrderLine.setBook(bookMapper.convertToEntity(orderLineDto.getBookDto()));
        existingOrderLine.setQuantity(orderLineDto.getQuantity());
        return existingOrderLine;
    }


    // delete
    @Transactional
    public void delete(Long id) {
        log.info("deleting by id");
        orderLineRepository.deleteById(id);
    }
}
