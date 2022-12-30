package com.example.networth.controllers;


import com.example.networth.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoleController {


    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/roles")
    public String roles(Model model){
        model.addAttribute("roles",roleService.findAll());
        return "roles/role";
    }
}
