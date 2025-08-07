package com.learn.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.learn.productservice.config.JwtService;
import com.learn.productservice.model.User;
import com.learn.productservice.repository.UserRepository;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody User user) {
        try {
            // Validations
            if (user.getName() == null || user.getName().trim().length() < 2) {
                throw new RuntimeException("Name must be at least 2 characters");
            }
            if (user.getEmail() == null || !user.getEmail().contains("@")) {
                throw new RuntimeException("Valid email is required");
            }
            if (user.getMobileNo() == null || user.getMobileNo().trim().length() < 10) {
                throw new RuntimeException("Mobile number must be at least 10 digits");
            }
            
            // Check if email already exists
            if (userRepository.findByEmail(user.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists. Please login.");
            }
            
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole("USER");
            userRepository.save(user);
            return Map.of("message", "User registered successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Registration failed. " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        
        User dbUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, dbUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(dbUser.getEmail(), dbUser.getRole());
        return Map.of("token", token);
    }
}