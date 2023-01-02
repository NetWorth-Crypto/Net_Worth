package com.example.networth.controllers;

import com.example.networth.models.Post;
import com.example.networth.models.User;
import com.example.networth.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;



@Controller
public class UserController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/sign-up")
    public String showSignupForm(Model model ){
        model.addAttribute("user", new User());

        return "users/sign-up";
    }

    @PostMapping("/sign-up")
    public String saveUser(@ModelAttribute User user, RedirectAttributes attributes){
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        userService.saveUser(user);
attributes.addFlashAttribute("success","You successfully registered! You can now login");
        return "redirect:/login";
    }


    @GetMapping("searchUser")
    public String search(Model model, String user) {
        System.out.println(user);

        List<User> lists = userService.getByUser(user);
        model.addAttribute("lists", lists);
        System.out.println(lists);
        return "users/searchUser";
    }

    @GetMapping("searchFollower")
    public String searchFollower(Model model, String user) {
        System.out.println(user);
        List<User> lists = userService.getByUser(user);
        model.addAttribute("lists", lists);
        System.out.println(lists);
        return "users/followers";
    }

    @GetMapping("searchFollowing")
    public String searchFollowing(Model model, String user) {
        System.out.println(user);
        List<User> lists = userService.getByUser(user);
        model.addAttribute("lists", lists);
        System.out.println(lists);
        return "users/following";
    }





}





