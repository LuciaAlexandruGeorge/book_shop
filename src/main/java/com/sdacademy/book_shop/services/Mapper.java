package com.sdacademy.book_shop.services;

public interface Mapper <E,D>{
    D convertToDto(E entity);
    E convertToEntity(D dto);
}
