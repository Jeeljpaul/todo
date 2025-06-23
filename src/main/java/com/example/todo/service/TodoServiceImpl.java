package com.example.todo.service;

import com.example.todo.dto.TodoDTO;
import com.example.todo.exception.AppException;
import com.example.todo.model.Todo;
import com.example.todo.repo.TodoRepository;
import com.example.todo.util.mapper.TodoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service

public class TodoServiceImpl implements TodoService{

    private final TodoRepository repo;
    private final TodoMapper mapper;

    public TodoServiceImpl(TodoRepository repo, TodoMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public TodoDTO addTodo(TodoDTO todoDTO) {
            Todo todo = mapper.toTodo(todoDTO);
            Todo savedDTO = repo.save(todo);
            return mapper.toTodoDTO(savedDTO);
    }


    @Override
    public List<TodoDTO> getAllTodos() {
            return repo.findAll().stream().map(mapper::toTodoDTO).collect(Collectors.toList());
    }


    @Override
    public TodoDTO getTodo(Integer id) {
            Todo todo = repo.findById(id).orElseThrow(() -> new AppException("Todo with not found", HttpStatus.NOT_FOUND));
            return mapper.toTodoDTO(todo);
    }

    @Override
    public TodoDTO updateTodo(Integer id, TodoDTO todoDTO) {
            Todo existing = repo.findById(id).orElseThrow(() -> new AppException("Todo not found", HttpStatus.NOT_FOUND));
            existing.setTask(todoDTO.getTask());
            existing.setCompleted(todoDTO.getCompleted());
            Todo savedTodo = repo.save(existing);
            return mapper.toTodoDTO(savedTodo);
    }


    @Override
    public void deleteTodo(Integer id) {
            if (!repo.existsById(id)) {

                throw new AppException("Todo not found for deletion", HttpStatus.NOT_FOUND);
            }
            repo.deleteById(id);
    }
}


