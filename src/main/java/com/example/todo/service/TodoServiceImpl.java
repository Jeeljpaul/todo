package com.example.todo.service;

import com.example.todo.dto.TodoDTO;
import com.example.todo.exception.AppException;
import com.example.todo.model.Todo;
import com.example.todo.repo.TodoRepository;
import com.example.todo.util.mapper.TodoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.stream.Collectors;


@Service

public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepo;
    private final TodoMapper todoMapper;
    private static final Logger logger = LogManager.getLogger(TodoServiceImpl.class);


    public TodoServiceImpl(TodoRepository repo, TodoRepository todoRepo, TodoMapper mapper) {
        this.todoRepo = todoRepo;
        this.todoMapper = mapper;
    }

    @Override
    public TodoDTO addTodo(TodoDTO todoDTO) {
        logger.info("Adding new todo: {}", todoDTO.getTask());
        Todo todo = todoMapper.toTodo(todoDTO);
        Todo savedDTO = todoRepo.save(todo);
        logger.debug("Todo saved with ID: {}", savedDTO.getId());
        return todoMapper.toTodoDTO(savedDTO);
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        logger.info("Fetching all todos");
        List<TodoDTO> todos = todoRepo.findAll()
                .stream()
                .map(todoMapper::toTodoDTO)
                .collect(Collectors.toList());
        logger.debug("Total todos fetched: {}", todos.size());
        return todos;
    }

    @Override
    public TodoDTO getTodo(Integer id) {
        logger.info("Fetching todo with ID: {}", id);
        Todo todo = todoRepo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Todo with ID {} not found", id);
                    return new AppException("Todo not found", HttpStatus.NOT_FOUND);
                });
        return todoMapper.toTodoDTO(todo);
    }

    @Override
    public TodoDTO updateTodo(Integer id, TodoDTO todoDTO) {
        logger.info("Updating todo with ID: {}", id);
        Todo existingTodo = todoRepo.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Todo with ID {} not found for update", id);
                    return new AppException("Todo not found", HttpStatus.NOT_FOUND);
                });
        existingTodo.setTask(todoDTO.getTask());
        existingTodo.setCompleted(todoDTO.getCompleted());
        Todo updatedTodo = todoRepo.save(existingTodo);
        logger.debug("Todo updated: {}", updatedTodo.getId());
        return todoMapper.toTodoDTO(updatedTodo);
    }

    @Override
    public void deleteTodo(Integer id) {
        logger.info("Deleting todo with ID: {}", id);
        if (!todoRepo.existsById(id)) {
            logger.warn("Todo with ID {} not found for deletion", id);
            throw new AppException("Todo not found for deletion", HttpStatus.NOT_FOUND);
        }
        todoRepo.deleteById(id);
        logger.info("Todo with ID {} deleted", id);
    }
}


