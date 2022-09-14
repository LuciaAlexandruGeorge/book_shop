package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.BookCategoryDto;
import com.sdacademy.book_shop.entities.book.Book;
import com.sdacademy.book_shop.entities.book.BookCategory;
import com.sdacademy.book_shop.exceptions.ResourceAlreadyExistsException;
import com.sdacademy.book_shop.repository.BookCategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookCategoryService {
    private static final Logger log = LoggerFactory.getLogger(BookCategoryService.class);

    private final BookCategoryRepository bookCategoryRepository;

    @Autowired
    public BookCategoryService(BookCategoryRepository bookCategoryRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
    }


    // create
    public void save(BookCategory bookCategory) {
        log.info("saving product category");
        bookCategoryRepository.save(bookCategory);
    }

    // find all
    public List<BookCategory> findAll() {
        log.info("finding all categories");
        return bookCategoryRepository.findAll();
    }

    // find by id
    public BookCategory findById(Long id) {
        log.info("finding by id");
        return bookCategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("category not found"));
    }

    // update
    public void update(Long id, BookCategoryDto bookCategoryDto) {
        log.info("update product {}", bookCategoryDto);
        BookCategory existingBookCategory=bookCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("product category not found"));
        existingBookCategory.setName(bookCategoryDto.getName());

        bookCategoryRepository.save(existingBookCategory);


    }



    public void updateNew(BookCategory bookCategory) {
        log.info("update product category {}", bookCategory);

        String name = bookCategory.getName();
        bookCategoryRepository.findByNameIgnoreCase(name)
                .filter(existingProductCategory -> existingProductCategory.getId().equals(bookCategory.getId()))
                .map(existingProductCategory -> bookCategoryRepository.save(bookCategory))
                .orElseThrow(() -> {
                    log.error("product category with name {} already exists", name);
                    throw new ResourceAlreadyExistsException("product category with name " + name + " already exists");
                });
    }

    // delete
    @Transactional
    public void delete(Long id) {
        log.info("deleting by id");
        bookCategoryRepository.deleteById(id);
    }
}
