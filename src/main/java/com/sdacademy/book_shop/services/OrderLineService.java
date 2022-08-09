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

    private static final Logger log = LoggerFactory.getLogger(OrderLineService.class);

    private final OrderLineRepository orderLineRepository;

    @Autowired
    public OrderLineService(OrderLineRepository orderLineRepository) {
        this.orderLineRepository = orderLineRepository;
    }

    // create
    public void save(OrderLineDto orderLine) {
        log.info("saving orderline {}", orderLine.getId());
        orderLineRepository.save(orderLine);
    }

//    // find all
//    public List<Product> findAll() {
//        log.info("finding all orderline");
//        return productRepository.findAll();
//    }

    // find by id
    public OrderLine findById(Long id) {
        log.info("finding by id");
        return orderLineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderLine not found"));
    }

    // update
    public void update(Long orderLineId, OrderLineDto orderLineDTO) {
        log.info("update Orderline {}", orderLineDTO.getBook());

        orderLineRepository.findById(orderLineId)
                .map(existingOrderLine -> updateEntity(orderLineDTO, existingOrderLine))
                .map(updatedOrderLine -> orderLineRepository.save(updatedOrderLine))
                .orElseThrow(() -> new RuntimeException("OrderLine not found"));
    }

    private OrderLine updateEntity(OrderLineDto orderLineData, OrderLine existingOrderLine) {
        existingOrderLine.setBook(orderLineData.getBook());
        existingOrderLine.setQuantity(orderLineData.getQuantity());
        return existingOrderLine;
    }

//    public void updateNew(Product product) {
//        log.info("update product {}", product);
//
//        String name = product.getName();
//        productRepository.findByNameIgnoreCase(name)
//                .filter(existingProduct -> existingProduct.getId().equals(product.getId()))
//                .map(existingProduct -> productRepository.save(product))
//                .orElseThrow(() -> {
//                    log.error("product with name {} already exists", name);
//                    throw new ResourceAlreadyExistsException("product with name " + name + " already exists");
//                });
//    }

    // delete
    @Transactional
    public void delete(Long id) {
        log.info("deleting by id");
        orderLineRepository.deleteById(id);
    }
}
