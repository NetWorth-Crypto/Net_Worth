package com.example.networth.controllers;


import com.example.networth.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
    public class AuthenticationController {

    private final UserRepository userDao;

    public AuthenticationController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }
    }

