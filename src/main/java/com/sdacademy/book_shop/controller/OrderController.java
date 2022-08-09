package com.sdacademy.book_shop.controller;

import com.sdacademy.book_shop.dto.BookDto;
import com.sdacademy.book_shop.dto.OrderDto;
import com.sdacademy.book_shop.dto.OrderLineDto;
import com.sdacademy.book_shop.services.BookService;
import com.sdacademy.book_shop.services.OrderLineService;
import com.sdacademy.book_shop.services.OrderService;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static lombok.AccessLevel.PRIVATE;

@Controller
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);


     OrderService orderService;
     OrderLineService orderLineService;
     BookService bookService;


    @PostMapping("/order/add/{bookId}")
    public String add(@PathVariable("bookId") long bookId, Model model) {
        //1. Check if order exists, if not create one
        OrderDto order = (OrderDto) model.getAttribute("orderId");
        BookDto book = bookService.findById(bookId);
        orderBuilder(model, order);

        //Add product to order

        //2. Create orderLine from book;
        //trigger - butonu de pe pagina menu
        OrderLineDto orderLine = OrderLineDto.builder()
                .bookDto(book)
                .quantity(1)
                .order(order)
                .price(book.getPrice())
                .build();


        orderLineService.save(orderLine);

        //Check if there's multiple orderLines for the same product
        return "redirect:/menu/";
    }

    private void orderBuilder(Model model, OrderDto order) {
        if (order == null) {
            //Create order and add order to model
            OrderDto newOrder = new OrderDto();
            newOrder= orderService.save(newOrder);
            model.addAttribute("orderId", newOrder);
        }
    }


}
