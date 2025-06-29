package com.example.todo.service;

import com.example.todo.dto.TodoDTO;
import com.example.todo.dto.UserDTO;
import com.example.todo.model.User;

import java.util.List;

public interface AuthService {
    User registerUser(UserDTO userDTO);

    List<TodoDTO> getTodosByUsername(String name);

}
