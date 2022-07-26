package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.BookDto;
import com.sdacademy.book_shop.dto.OrderDto;
import com.sdacademy.book_shop.entities.book.Book;
import com.sdacademy.book_shop.entities.cartNoder.OrderCommand;
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

    private Map<BookDto, Integer> books = new HashMap<>();


    public void addBook(BookDto book, int quantity) {
        if (books.containsKey(book)) {
            books.replace(book, books.get(book) + quantity);
        } else {
            books.put(book, quantity);
        }
    }

    public void removeBook(BookDto book) {
        if (books.containsKey(book)) {
            if (books.get(book) > 1)
                books.replace(book, books.get(book) - 1);
            else if (books.get(book) == 1) {
                books.remove(book);
            }
        }
    }

    public Map<BookDto, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(books);
    }


    //TODO creaza legatura user - order
    public void checkout() throws NotEnoughBooksInStockException {
        Book book;
        for (Map.Entry<BookDto, Integer> entry : books.entrySet()) {
            Long productKey = entry.getKey().getId();
            // Refresh quantity for every book before checking
            book = bookRepository.findById(productKey).orElseThrow();

            if (book.getQuantity() < entry.getValue()) {
                throw new NotEnoughBooksInStockException(book);
            }

            book.setQuantity(book.getQuantity() - entry.getValue());

            bookRepository.save(book);
        }


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
        OrderCommand orderCommandEntity = orderMapper.convertToEntity(order);
        orderRepository.save(orderCommandEntity);
        return orderMapper.convertToDto(orderCommandEntity);
    }

    // find all
    public List<OrderDto> findAll() {
        log.info("finding all order");
        return orderRepository.findAll().stream().map(o -> orderMapper.convertToDto(o)).collect(Collectors.toList());
    }

    // find by id
    public OrderDto findById(Long id) {
        log.info("finding by id");
        OrderCommand entity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return orderMapper.convertToDto(entity);
    }

    // update
    public void update(Long orderId, OrderDto orderDto) {
        log.info("update Order {}", orderDto.getEntries().toString());
        OrderCommand orderCommand = orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
        updateEntity(orderDto, orderCommand);
        orderRepository.save(orderCommand);

    }

    private void updateEntity(OrderDto orderDto, OrderCommand existingOrderCommand) {
        existingOrderCommand.setTotalPrice(orderDto.getTotalPrice());
        existingOrderCommand.setAddress(orderDto.getAddress());
        existingOrderCommand.setOrderDate(orderDto.getOrderDate());
        if (orderDto.getEntries() != null) {
            existingOrderCommand.setEntries(orderDto.getEntries().stream().map(e -> orderLineMapper.convertToEntity(e)).collect(Collectors.toList()));
        }
        if (orderDto.getUser() != null) {
            existingOrderCommand.setUser(userMapper.convertToEntity(orderDto.getUser()));
        }
        existingOrderCommand.setOrderStatus(orderDto.getOrderStatus());
    }


    // delete
    @Transactional
    public void delete(Long id) {
        log.info("deleting by id");
        orderRepository.deleteById(id);
    }
}
