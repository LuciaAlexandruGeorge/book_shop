package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.UserDto;
import com.sdacademy.book_shop.entities.user.User;
import com.sdacademy.book_shop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapper implements Mapper<User, UserDto> {
   private UserRepository userRepository;
    @Override
    public UserDto convertToDto(User entity){
        UserDto result = new UserDto();
        result.setId(entity.getId());
        result.setName(entity.getName());
        result.setOrderList(entity.getOrderList());
        result.setEmail(entity.getEmail());
        result.setPassword(entity.getPassword());
        result.setAddress(entity.getAddress());
        result.setRoles(entity.getRoles());
        return result;
    }

    @Override
    public User convertToEntity(UserDto dto){
        User result = new User();
        if (dto.getId()!=null){
            result=userRepository.findById(dto.getId()).orElse(result);
        }
        result.setName(dto.getName());
        result.setOrderList(dto.getOrderList());
        result.setEmail(dto.getEmail());
        result.setPassword(dto.getPassword());
        result.setAddress(dto.getAddress());
        result.setRoles(dto.getRoles());
        return result;
    }
}
