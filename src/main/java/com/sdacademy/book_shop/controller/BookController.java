package com.sdacademy.book_shop.controller;

import com.sdacademy.book_shop.dto.BookDto;
import com.sdacademy.book_shop.entities.book.BookCategory;
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
    @GetMapping("")
    public String showBooks(Model model) {
        List<BookDto> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/create")
    public String showForm(Model model) {
        model.addAttribute("bookForm", new BookDto());
        return "book_create";
    }

    @PostMapping("/create")
    public String createBook(@ModelAttribute("bookForm") @Valid BookDto form, Errors errors, Model model) {
        if (errors.hasErrors()) {
            return "book_create";
        }
        bookService.createBook(form);
        return "redirect:/books";
    }

    @GetMapping("/edit/{bookId}")
    public String showEditForm(@PathVariable("bookId") long id, Model model) {//Model e modelul din Spring MVC
        BookDto bookForm = bookService.findById(id);
        model.addAttribute("bookForm", bookForm);
        return "book_create";
    }
    @GetMapping("/delete/{bookId}")
    public String deleteBook(@PathVariable("bookId") int id, Model model) {//Model e modelul din Spring MVC
        bookService.deleteById(id);
        return "redirect:/books";
    }

    @GetMapping(path = "/category/{category}")
    public @ResponseBody List<BookDto> getByBookCategory(@PathVariable BookCategory category){return bookService.getAllByCategory(category);}

    @GetMapping(path = "/title/{title}")
    public @ResponseBody List<BookDto> getByTitleIgnoreCase(@PathVariable String title){return bookService.getByTitleIgnoreCase(title);}

    @GetMapping(path = "/author/{name}")
    public @ResponseBody List<BookDto> getAllByAuthor_FirstNameOrLastName(@PathVariable String name){return  bookService.getAllByAuthor_FirstNameOrLastName(name);}



    @GetMapping(path = "/isbn/{isbn}")
    public @ResponseBody BookDto getByISBN(@PathVariable(name = "isbn") String ISBN) {
        return bookService.getByISBN(ISBN);
    }

    @GetMapping("/author_isbn")
    public @ResponseBody BookDto getByAuthorAndISBN(@RequestParam String author, @RequestParam(name = "isbn") String ISBN) {
        return bookService.getByAuthorAndISBN(author, ISBN);
    }

    @GetMapping(path = "/top/{author}")
    public @ResponseBody List<BookDto> getTop3ByAuthorOrderByPagesNumDesc(@PathVariable String author) {
        return bookService.getTop3ByAuthorOrderByPagesNumDesc(author);
    }

    @GetMapping(path = "/starts-with/{title}")
    public @ResponseBody List<BookDto> getByTitleStartingWith(@PathVariable String title) {
        return bookService.getByTitleStartingWith(title);
    }

    @GetMapping(path = "/range-pages")
    public @ResponseBody List<BookDto> getAllByPagesNumBetween(@RequestParam Integer min, @RequestParam(required = false) Integer max) {
        if (max == null) {
            return bookService.getWherePagesNumIsGreaterThanX(min);
        }
        return bookService.getAllByPagesNumBetween(min, max);
    }

    @GetMapping(path = "/range-price")
    public @ResponseBody List<BookDto> getAllByPriceBetween(@RequestParam Integer min, @RequestParam(required = false) Integer max) {
        if (max == null) {
            return bookService.getWherePriceIsGreaterThanX(min);
        }
        return bookService.getAllByPriceBetween(min, max);
    }

    @GetMapping(path = "/range-price-greater")
    public @ResponseBody List<BookDto> getWherePriceIsGreaterThanX(@RequestParam Integer min){
       return bookService.getWherePriceIsGreaterThanX(min);
    }

}