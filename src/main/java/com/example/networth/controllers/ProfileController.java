package com.example.networth.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController
{
    @GetMapping("/profile")
    public String profile()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        return "users/profile";
    }
}
