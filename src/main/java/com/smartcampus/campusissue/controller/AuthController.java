package com.smartcampus.campusissue.controller;

import com.smartcampus.campusissue.model.User;
import com.smartcampus.campusissue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Register
    @PostMapping("/register")
    public User register(@RequestBody User user) {

        user.setRole("STUDENT"); // default role
        return userRepository.save(user);
    }

    // Login
    @PostMapping("/login")
    public User login(@RequestBody User loginUser) {

        User user = userRepository.findByEmail(loginUser.getEmail());

        if(user != null && user.getPassword().equals(loginUser.getPassword())) {
            return user;
        }

        throw new RuntimeException("Invalid email or password");
    }
}