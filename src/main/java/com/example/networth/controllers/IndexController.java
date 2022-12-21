package com.example.networth.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(){
        return "landing";
    }

    @GetMapping("/new")
    public String newIndex(){
        return "newLandingPage";
    }

}
