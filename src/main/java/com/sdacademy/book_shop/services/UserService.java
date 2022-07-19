package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.UserDto;
import com.sdacademy.book_shop.entities.User;
import com.sdacademy.book_shop.exceptions.DuplicateUserException;
import com.sdacademy.book_shop.exceptions.UserDoesNotExistException;
import com.sdacademy.book_shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    ModelMapper modelMapper;

    public List<UserDto> getAll() {
        log.debug("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(toList());
    }

    public List<UserDto> getByName(String username) {
        return userRepository.findByName(username)
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(toList());
    }

    public void createUser(final UserDto userDto) {
        val username = userDto.getUsername();
        if (userRepository.existsByUsername(username)) {
            log.error("This username already exists");
            throw new DuplicateUserException("Duplicate user");
        }
        val id = userRepository.save(User.builder()
                .username(username)
                .age(userDto.getAge())
                .city(userDto.getCity())
                .rol(userDto.getRol())
                .build()
        ).getId();


    }

    public void update(final UserDto userDto) {
        val id = userDto.getId();
        isUserExistentById(id);
        userRepository.save(User.builder()
                .username(userDto.getUsername())
                .city(userDto.getCity())
                .age(userDto.getAge())
                .id(id)
                .build()
        );
        log.info("The user with id {} was updated", id);
    }

    public void deleteUserById(final int id) {
        isUserExistentById(id);
        userRepository.deleteById(id);
        log.info("User with id {} has been deleted", id);
    }

    private void isUserExistentById(int id) {
        if (!userRepository.existsById(id)) {
            log.error("The user with id {} doesn't exist", id);
            throw new UserDoesNotExistException("Non existent id");
        }
    }
}