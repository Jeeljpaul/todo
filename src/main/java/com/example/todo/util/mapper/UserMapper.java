package com.example.todo.util.mapper;

import com.example.todo.dto.UserDTO;
import com.example.todo.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO entityToUser(User user) {
        UserDTO userDto = new UserDTO();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    public User userToEntity(UserDTO userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }
}