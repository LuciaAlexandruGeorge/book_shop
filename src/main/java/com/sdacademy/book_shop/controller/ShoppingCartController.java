package com.sdacademy.book_shop.controller;

import com.sdacademy.book_shop.dto.BookDto;
import com.sdacademy.book_shop.exceptions.NotEnoughBooksInStockException;
import com.sdacademy.book_shop.services.BookService;
import com.sdacademy.book_shop.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ShoppingCartController {
    private final OrderService orderService;
    private final BookService bookService;

    @Autowired
    public ShoppingCartController(OrderService orderService, BookService bookService) {
        this.orderService = orderService;
        this.bookService = bookService;
    }

    @GetMapping("/shoppingCart")
    public ModelAndView shoppingCart() {
        ModelAndView modelAndView = new ModelAndView("/shoppingCart");
        modelAndView.addObject("books", orderService.getProductsInCart());
        modelAndView.addObject("total", orderService.getTotal());
        return modelAndView;
    }

    @GetMapping("/shoppingCart/addBook/{bookId}")
    public ModelAndView addBookToCart(@PathVariable("bookId") Long bookId, @RequestParam("productQuantity") int quantity) {
        BookDto book = bookService.findById(bookId);
        if (book != null) {
            orderService.addBook(book, quantity);
        }
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/removeBook/{bookId}")
    public ModelAndView removeBookFromCart(@PathVariable("bookId") Long bookId) {
        BookDto book = bookService.findById(bookId);
        if (book != null) {
            orderService.removeBook(book);
        }
        return shoppingCart();
    }

    @GetMapping("/shoppingCart/checkout")
    public ModelAndView checkout() {
        try {
            orderService.checkout();
        } catch (NotEnoughBooksInStockException e) {
            return shoppingCart().addObject("outOfStockMessage", e.getMessage());
        }
        return shoppingCart();
    }
}
