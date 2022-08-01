package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.AuthorDto;
import com.sdacademy.book_shop.entities.Author;
import com.sdacademy.book_shop.exceptions.EntityNotFoundError;
import com.sdacademy.book_shop.repository.AuthorRepository;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public void createAuthor(AuthorDto form) {
        Author author = authorMapper.convertToEntity(form);
        authorRepository.save(author);
    }

    public AuthorDto findById(long id) {
        Author entityAuthor = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundError(String.format("Book with %s does not exist", id)));
        return authorMapper.convertToDto(entityAuthor);
    }

    public void deleteById(long id) {
        authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundError(String.format("Book with %s does not exist", id)));
        authorRepository.deleteById(id);
    }
}
