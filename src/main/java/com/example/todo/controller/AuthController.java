package com.example.todo.controller;

import com.example.todo.dto.LoginDTO;
import com.example.todo.dto.TodoDTO;
import com.example.todo.dto.UserDTO;
import com.example.todo.model.User;
import com.example.todo.repo.UserRepository;
import com.example.todo.service.AuthService;
import com.example.todo.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/users")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepo;

    public AuthController(AuthService authService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder, UserRepository userRepo) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
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
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try {
            System.out.println("Login attempt for user: " + loginDTO.getUsername());

            User user = userRepo.findByUsername(loginDTO.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            System.out.println("User found. Encoded password: " + user.getPassword());
            System.out.println("Password match: " + passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()));

            if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }

            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(token);

        } catch (Exception e) {
            e.printStackTrace();  // Print full stack trace in logs
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error: " + e.getMessage());
        }
    }



}
