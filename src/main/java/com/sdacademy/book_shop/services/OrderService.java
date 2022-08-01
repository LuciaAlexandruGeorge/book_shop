package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.OrderBookDto;
import com.sdacademy.book_shop.entities.book.Book;
import com.sdacademy.book_shop.entities.cartNoder.Orders;
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

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookRepository bookRepository;

    private Map<Book, Integer> products = new HashMap<>();



    public void addProduct(Book book, int quantity) {
        if (products.containsKey(book)) {
            products.replace(book, book.get(book) + quantity);
        } else {
            products.put(book, quantity);
        }
    }

    public void removeProduct(Book book) {
        if (products.containsKey(book)) {
            if (products.get(book) > 1)
                products.replace(book, products.get(book) - 1);
            else if (products.get(book) == 1) {
                products.remove(book);
            }
        }
    }

    ;

    public Map<Book, Integer> getProductsInCart() {
        return Collections.unmodifiableMap(products);
    }


    //TODO creaza legatura user - order
    public void checkout() throws NotEnoughBooksInStockException {
        Book book;
        for (Map.Entry<Book, Integer> entry : products.entrySet()) {
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
        products.clear();
    }

    public double getTotal() {
        return products.entrySet().stream()
                .map(entry -> BigDecimal.valueOf(entry.getKey().getPrice() * entry.getValue()))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO).doubleValue();

    }


    // create
    public void save(Orders order) {
        log.info("saving order {}", order.getId());
        orderRepository.save(order);
    }

    // find all
    public List<Orders> findAll() {
        log.info("finding all order");
        return orderRepository.findAll();
    }

    // find by id
    public Orders findById(Long id) {
        log.info("finding by id");
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // update
    public void update(Long orderId, OrderBookDto orderBookDto) {
        log.info("update Order {}", orderBookDto.getEntries().toString());

        orderRepository.findById(orderId)
                .map(existingOrder -> updateEntity(orderDTO, existingOrder))
                .map(updatedOrder -> orderRepository.save(updatedOrder))
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    private Orders updateEntity(OrderRequest orderData, Orders existingOrder) {
        existingOrder.setTotalPrice(orderData.getTotalPrice());
        existingOrder.setAddress(orderData.getAddress());
        existingOrder.setOrderDate(orderData.getOrderDate());
        existingOrder.setEntries(orderData.getEntries());
        existingOrder.setUser(orderData.getUser());
        existingOrder.setOrderStatus(orderData.getOrderStatus());
        return existingOrder;
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
        orderRepository.deleteById(id);
    }
}
