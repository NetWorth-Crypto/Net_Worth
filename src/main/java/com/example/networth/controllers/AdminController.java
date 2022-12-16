package com.example.networth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController
{
    @GetMapping("/Admin")
    public String admin()
    {
        return "admin";
    }
}
