package com.sdacademy.book_shop.repository;

import com.sdacademy.book_shop.entities.user.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
