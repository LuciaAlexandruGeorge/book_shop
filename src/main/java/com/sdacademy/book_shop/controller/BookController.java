package com.sdacademy.book_shop.controller;

import com.sdacademy.book_shop.dto.BookDto;
import com.sdacademy.book_shop.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    public static final String BASE_URL = "/api/books";
    private final BookService bookService;
    @GetMapping("/books")
    public String showBooks(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/books/create")
    public String showForm(Model model) {
        model.addAttribute("bookForm", new BookDto());
        return "book_create";
    }

    @PostMapping("/books/create")
    public String createBook(@ModelAttribute("bookForm") @Valid BookDto form, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "book_create";
        }
        bookService.createBook(form);
        return "redirect:/books";
    }

    @GetMapping("/books/edit/{bookId}")
    public String showEditForm(@PathVariable("bookId") int id, Model model) {//Model e modelul din Spring MVC
        BookDto bookForm = bookService.findById(id);
        model.addAttribute("bookForm", bookForm);
        return "book_create";
    }
    @GetMapping("/books/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") int id, Model model) {//Model e modelul din Spring MVC
        bookService.deleteById(id);
        return "redirect:/books";
    }


    public List<BookDto> getByBookCategory

    @GetMapping(path = "/books/title/{title}")
    public List<BookDto> getByTitleIgnoreCase(@PathVariable String title){return bookService.getByTitleIgnoreCase(title);}

    @GetMapping(path = "/books/title/{name}")
    public List<BookDto> getAllByAuthor_FirstNameOrLastName(@PathVariable String name){return  bookService.getAllByAuthor_FirstNameOrLastName(name);}

    @GetMapping(path = "books/title/{title}")
    public List<BookDto> getAllByTitle(@PathVariable String title) {
        return bookService.getAllByTitle(title);
    }

    @GetMapping(path = "books/isbn/{isbn}")
    public BookDto getByISBN(@PathVariable(name = "isbn") String ISBN) {
        return bookService.getByISBN(ISBN);
    }

    @GetMapping
    public BookDto getByAuthorAndISBN(@RequestParam String author, @RequestParam(name = "isbn") String ISBN) {
        return bookService.getByAuthorAndISBN(author, ISBN);
    }

    @GetMapping(path = "books/top/{author}")
    public List<BookDto> getTop3ByAuthorOrderByPagesNumDesc(@PathVariable String author) {
        return bookService.getTop3ByAuthorOrderByPagesNumDesc(author);
    }

    @GetMapping(path = "books/starts-with/{title}")
    public List<BookDto> getByTitleStartingWith(@PathVariable String title) {
        return bookService.getByTitleStartingWith(title);
    }

    @GetMapping(path = "books/range-pages")
    public List<BookDto> getAllByPagesNumBetween(@RequestParam Integer min, @RequestParam(required = false) Integer max) {
        if (max == null) {
            return bookService.getWherePagesNumIsGreaterThanX(min);
        }
        return bookService.getAllByPagesNumBetween(min, max);
    }

    @GetMapping(path = "books/range-price")
    public List<BookDto> getAllByPriceBetween(@RequestParam Integer min, @RequestParam(required = false) Integer max) {
        if (max == null) {
            return bookService.getWherePriceIsGreaterThanX(min);
        }
        return bookService.getAllByPriceBetween(min, max);
    }

    @GetMapping(path = "books/range-price-greater")
    public List<BookDto> getWherePriceIsGreaterThanX(@RequestParam Integer min){
       return bookService.getWherePriceIsGreaterThanX(min);
    }

}