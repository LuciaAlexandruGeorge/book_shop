package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.BookDto;
import com.sdacademy.book_shop.entities.Author;
import com.sdacademy.book_shop.entities.book.Book;
import com.sdacademy.book_shop.repository.AuthorRepository;
import com.sdacademy.book_shop.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookMapper implements Mapper<Book, BookDto> {
    AuthorMapper authorMapper;
    BookRepository bookRepository;
    AuthorRepository authorRepository;
    @Override
    public BookDto convertToDto(Book entity) {
        BookDto result = new BookDto();
        if (entity.getAuthor() !=null) {
            result.setAuthorId(entity.getAuthor().getId());
            result.setAuthorDto(authorMapper.convertToDto(entity.getAuthor()));
        }
        result.setId(entity.getId());
        result.setTitle(entity.getTitle());
        return result;

    }

    @Override
    public Book convertToEntity(BookDto dto) {
        Book result = new Book();
        if (dto.getId()!=null){
            result = bookRepository.findById(dto.getId()).orElse(result);
        }
        if (dto.getAuthorId() !=null){
            Author author = authorRepository.findById(dto.getAuthorId()).orElse(new Author());
            result.setAuthor(author);
        }

        result.setId(dto.getId());
        result.setTitle(dto.getTitle());
        return result;
    }
}
