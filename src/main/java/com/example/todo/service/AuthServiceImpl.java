package com.example.todo.service;

import com.example.todo.dto.TodoDTO;
import com.example.todo.dto.UserDTO;
import com.example.todo.exception.AppException;
import com.example.todo.model.User;
import com.example.todo.repo.TodoRepository;
import com.example.todo.repo.UserRepository;
import com.example.todo.util.mapper.TodoMapper;
import com.example.todo.util.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final TodoRepository todoRepo;
    private final TodoMapper todoMapper;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepo, TodoRepository todoRepo, TodoMapper todoMapper, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.todoRepo = todoRepo;
        this.todoMapper = todoMapper;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        User user = userMapper.userToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public List<TodoDTO> getTodosByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));

        return user.getTodos()
                .stream()
                .map(todoMapper::toTodoDTO)
                .collect(Collectors.toList());
    }



}
