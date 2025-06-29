package com.example.todo.controller;

import com.example.todo.dto.TodoDTO;
import com.example.todo.dto.UserDTO;
import com.example.todo.model.User;
import com.example.todo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO){
        User saved = authService.registerUser(userDTO);
        return ResponseEntity.ok("User registered with ID: " + saved.getId());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<TodoDTO>> getUserTodos(Principal principal) {
        List<TodoDTO> todos = authService.getTodosByUsername(principal.getName());
        return ResponseEntity.ok(todos);
    }

}
