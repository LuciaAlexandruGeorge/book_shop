package com.sdacademy.book_shop.repository;


import com.sdacademy.book_shop.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAll();

    Optional<User> findByNameIgnoreCase(String name);

    User findByEmailIgnoreCase(String email);


}
