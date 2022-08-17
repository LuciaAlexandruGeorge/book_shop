package com.sdacademy.book_shop.controller;

import com.sdacademy.book_shop.dto.BookCategoryDto;
import com.sdacademy.book_shop.entities.book.BookCategory;
import com.sdacademy.book_shop.services.BookCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BookCategoryController {
    private static final Logger log = LoggerFactory.getLogger(BookCategoryController.class);

    private final BookCategoryService bookCategoryService;

    @Autowired
    public BookCategoryController(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }




    // http://localhost:8080/bookss

    // map url to controller method

//    @GetMapping("/")
//    public String showFirstBooksPage(Model model) {
//        // return a html page with books
//        // add list of books
//        List<BookCategory> bookCategories = BookCategoryService.findAll();
//        model.addAttribute("bookCategoriesInView", bookCategories);
//
//        // resolved by the view resolver
//        return "index";
//    }

    @GetMapping("/admin/book-categories")
    public String showProductCategoriesPage(Model model) {
        // return a html page with books
        // add list of books
        List<BookCategory> bookCategories = bookCategoryService.findAll();
        model.addAttribute("bookCategoriesInView", bookCategories);

        System.out.println("asd");
        log.info("Show category list");

        // resolved by the view resolver
        return "index-book-category";
    }


    @GetMapping("/admin/book-categories/add")
    public String showAddFrom(Model model) {
        BookCategory newBookCategory = new BookCategory();
        model.addAttribute("bookCategory", newBookCategory);
//        return "nav-bar";
        return "book-category-add";
    }

    @PostMapping("/admin/book-categories/add")
    public String add(@ModelAttribute BookCategory bookCategory) {
        bookCategoryService.save(bookCategory);
        return "redirect:/admin/book-categories/";
    }

    @GetMapping("/admin/book-categories/{id}/edit")
    public String showEditForm(Model model,
                               @PathVariable Long id) {

        model.addAttribute("bookCategory", bookCategoryService.findById(id));
        return "book-category-edit";
    }

    public ModelAndView showEditForm2(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("bookCate-edit");
        modelAndView.addObject("bookCategory", bookCategoryService.findById(id));
        return modelAndView;
    }

    @PostMapping("/admin/book-categories/{id}/edit")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute BookCategoryDto bookCategoryDto) {

        bookCategoryService.update(id, bookCategoryDto);
        return "redirect:/admin/book-categories/";
    }

    @GetMapping("/admin/book-categories/{id}/delete")
    public String delete(@PathVariable long id) {
        bookCategoryService.delete(id);
        return "redirect:/admin/book-categories/";
    }
}

