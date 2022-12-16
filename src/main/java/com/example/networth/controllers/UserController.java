package com.example.networth.controllers;

import com.example.networth.models.User;
import com.example.networth.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/sign-up")
    public String showSignupForm(Model model){
        model.addAttribute("user", new User());
        System.out.println("reached");
        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userService.saveUser(user);

        return "redirect:/login";
    }

//    @GetMapping("/followers")
//    public String followers()
//    {
//        return "users/followers";
//    }
//
//    @GetMapping("/following")
//    public String following(){
//        return "users/following";
//    }





}





