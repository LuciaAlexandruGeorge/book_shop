package com.sdacademy.book_shop.dto;

import com.sdacademy.book_shop.enums.UserRol;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class UserDto {
    int id;
    @NotBlank(message = "Username must be provided")
    String username;
    @NotNull(message = "Age must be provided")
    @Positive(message = "Age must be positive")
    int age;
    @NotBlank(message = "City must be provided")
    String city;
    @NotBlank(message = "User rol must be provided")
    UserRol rol;
}
