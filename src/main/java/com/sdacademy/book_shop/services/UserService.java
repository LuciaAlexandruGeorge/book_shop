package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.UserDto;
import com.sdacademy.book_shop.entities.user.User;
import com.sdacademy.book_shop.exceptions.ResourceAlreadyExistsException;
import com.sdacademy.book_shop.repository.UserRepository;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Service
public class UserService implements UserDetailsService {

    static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserMapper userMapper;
    final UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Used by Spring Security to identify and pass and UserDetails to its classes
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        List roles = new ArrayList();
        String role = "ROLE_" + user.getRoles();
        roles.add(new SimpleGrantedAuthority(role));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), roles);
    }

    // create

    public void save(UserDto user) {
        log.info("saving user {}", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRoles() == null) {
            user.setRoles("USER");
        }
        User userEntity = userMapper.convertToEntity(user);
        userRepository.save(userEntity);
    }
    // find all

    public List<User> getAll() {
        log.info("finding all users");
        return userRepository.findAll();
    }
    // find by id

    public User findById(Long id) {
        log.info("finding by id");
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("user not found"));
    }
    // update

    public void update(Long userId, UserDto userDto) {
        log.info("update user {}", userDto);

        userRepository.findById(userId)
                .map(existingUser -> updateEntity(userDto, existingUser))
                .map(updatedUser -> userRepository.save(updatedUser))
                .orElseThrow(() -> new RuntimeException("user not found"));
    }

    private User updateEntity(UserDto userDto, User existingUser) {
        existingUser.setName(userDto.getName());
        existingUser.setOrderList(userDto.getOrderList());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        existingUser.setAddressList(userDto.getAddressList());
        existingUser.setRoles(userDto.getRoles());
        existingUser.setThumbnail(userDto.getThumbnail());
        return existingUser;
    }

    public void updateNew(User user) {
        log.info("update user {}", user);

        String name = user.getName();
        userRepository.findByNameIgnoreCase(name)
                .filter(existingUser -> existingUser.getId().equals(user.getId()))
                .map(existingUser -> userRepository.save(user))
                .orElseThrow(() -> {
                    log.error("user with name {} already exists", name);
                    throw new ResourceAlreadyExistsException("user with name " + name + " already exists");
                });
    }
    // delete

    @Transactional
    public void delete(Long id) {
        log.info("deleting by id");
        userRepository.deleteById(id);
    }
}

