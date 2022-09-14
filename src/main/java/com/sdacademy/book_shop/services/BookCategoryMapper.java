package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.BookCategoryDto;
import com.sdacademy.book_shop.entities.book.BookCategory;
import org.springframework.stereotype.Component;


@Component
public class BookCategoryMapper {

    public BookCategoryDto convertToDto(BookCategory entity) {
        BookCategoryDto result = new BookCategoryDto();
        result.setId(entity.getId());
        result.setName(entity.getName());

        return result;
    }


    public BookCategory convertToEntity(BookCategoryDto dto){
        BookCategory result = new BookCategory();
        result.setId(dto.getId());
        result.setName(dto.getName());

        return result;
    }
}
