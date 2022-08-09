package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.OrderDto;
import com.sdacademy.book_shop.entities.book.Book;
import com.sdacademy.book_shop.entities.cartNoder.Order;
import com.sdacademy.book_shop.exceptions.NotEnoughBooksInStockException;
import com.sdacademy.book_shop.repository.BookRepository;
import com.sdacademy.book_shop.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService {
    @Autowired
    OrderMapper orderMapper;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
   private OrderLineMapper orderLineMapper;

    private Map<Book, Integer> books = new HashMap<>();



//    public void addProduct(Book book, int quantity) {
//        if (books.containsKey(book)) {
//            books.replace(book, book.get(book) + quantity);
//        } else {
//            books.put(book, quantity);
//        }
//    }
//
//    public void removeProduct(Book book) {
//        if (books.containsKey(book)) {
//            if (books.get(book) > 1)
//                books.replace(book, books.get(book) - 1);
//            else if (books.get(book) == 1) {
//                books.remove(book);
//            }
//        }
//    }

    ;

    public Map<Book, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(books);
    }


    //TODO creaza legatura user - order
    public void checkout() throws NotEnoughBooksInStockException {
        Book book;
        for (Map.Entry<Book, Integer> entry : books.entrySet()) {
            Long productKey = entry.getKey().getId();
            // Refresh quantity for every product before checking
            book = bookRepository.findById(productKey).orElseThrow();

            if (book.getQuantity() < entry.getValue())
                throw new NotEnoughBooksInStockException(book);

            entry.getKey().setQuantity(book.getQuantity() - entry.getValue());

            bookRepository.save(entry.getKey());
        }

//        productRepository.save(products.keySet());
        bookRepository.flush();
        books.clear();
    }

    public double getTotal() {
        return books.entrySet().stream()
                .map(entry -> BigDecimal.valueOf(entry.getKey().getPrice() * entry.getValue()))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO).doubleValue();

    }


    // create
    public OrderDto save(OrderDto order) {
        log.info("saving order {}", order.getId());
        Order orderEntity= orderMapper.convertToEntity(order);
        orderRepository.save(orderEntity);
        return orderMapper.convertToDto(orderEntity);
    }

    // find all
    public List<OrderDto> findAll() {
        log.info("finding all order");
        return orderRepository.findAll().stream().map(o->orderMapper.convertToDto(o)).collect(Collectors.toList());
    }

    // find by id
    public OrderDto findById(Long id) {
        log.info("finding by id");
        Order entity =  orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.convertToDto(entity);
    }

    // update
    public void update(Long orderId, OrderDto orderDto) {
        log.info("update Order {}", orderDto.getEntries().toString());
        Order order= orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        updateEntity(orderDto,order);
        orderRepository.save(order);

    }

    private void updateEntity(OrderDto orderData, Order existingOrder) {
        existingOrder.setTotalPrice(orderData.getTotalPrice());
        existingOrder.setAddress(orderData.getAddress());
        existingOrder.setOrderDate(orderData.getOrderDate());
        if (orderData.getEntries()!=null) {
            existingOrder.setEntries(orderData.getEntries().stream().map(e->orderLineMapper.convertToEntity(e)).collect(Collectors.toList()));
        }
        if(orderData.getUser()!=null){
            existingOrder.setUser(userMapper.convertToEntity(orderData.getUser()));
        }
        existingOrder.setOrderStatus(orderData.getOrderStatus());
    }



    // delete
    @Transactional
    public void delete(Long id) {
        log.info("deleting by id");
        orderRepository.deleteById(id);
    }
}
