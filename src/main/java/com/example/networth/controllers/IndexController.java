package com.example.networth.controllers;

import com.example.networth.SecurityConfiguration;
import com.example.networth.models.User;
import com.example.networth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")

    public String index(){

        User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user.getPassword());
        return "newLandingPage";
    }

    @GetMapping("/new")
    public String newIndex(){
        return "newLandingPage";
    }



}
