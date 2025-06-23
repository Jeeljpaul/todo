package com.example.todo.service;


import com.example.todo.dto.TodoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoService {

    TodoDTO addTodo(TodoDTO todoDTO);

    List<TodoDTO> getAllTodos();

    TodoDTO getTodo(Integer id);

    TodoDTO updateTodo(Integer id, TodoDTO todoDTO);

    void deleteTodo(Integer id);
}
