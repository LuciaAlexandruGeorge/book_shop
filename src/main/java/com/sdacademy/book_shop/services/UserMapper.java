package com.sdacademy.book_shop.services;

import com.sdacademy.book_shop.dto.UserDto;
import com.sdacademy.book_shop.entities.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements Mapper<User, UserDto> {
    @Override
    public UserDto convertToDto(User entity){
        UserDto result = new UserDto();
        result.setId(result.getId());
        result.setOrderList(result.getOrderList());
        result.setEmail(entity.getEmail());
        result.setPassword(entity.getPassword());
        result.setAddressList(result.getAddressList());
        result.setRoles(result.getRoles());
        return result;
    }

    public User convertToEntity(UserDto dto){
        User result = new User();
        result.setName(dto.getName());
        result.setOrderList(dto.getOrderList());
        result.setEmail(dto.getEmail());
        result.setPassword(dto.getPassword());
        result.setAddressList(dto.getAddressList());
        result.setRoles(dto.getRoles());
        return result;
    }
}
