package com.example.todo.controller;


import com.example.todo.dto.TodoDTO;
import com.example.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoDTO> addTodo(@Valid @RequestBody TodoDTO todoDTO){

            TodoDTO savedDTO = todoService.addTodo(todoDTO);
            return new ResponseEntity<>(savedDTO, HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos(){
            List<TodoDTO> todos = todoService.getAllTodos();
            return new ResponseEntity<>(todos, HttpStatus.OK);

    }


    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodo(@PathVariable Integer id){
            TodoDTO todo = todoService.getTodo(id);
            return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable Integer id, @RequestBody TodoDTO todoDTO){
            TodoDTO updatedDTO = todoService.updateTodo(id, todoDTO);
            return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Integer id){
            todoService.deleteTodo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }}
