package com.sdacademy.book_shop.repository;

import com.sdacademy.book_shop.entities.Book;
import com.sdacademy.book_shop.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    void create(User user);

    void delete(User user);

    void update(User user);

    Optional<User> findByName(String username);

    Optional<List<User>> findAll ();

    boolean existsByUsername(final String username);

}
