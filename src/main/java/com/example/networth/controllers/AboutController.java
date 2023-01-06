package com.example.networth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController
{
    @GetMapping("/userProfile")
    public String about()
    {
        return "about";
    }
}
