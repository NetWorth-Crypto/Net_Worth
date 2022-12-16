package com.example.networth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreatePostController
{
    @GetMapping("create-post")
    public String createPost()
    {
        return "users/create-post";
    }
}
