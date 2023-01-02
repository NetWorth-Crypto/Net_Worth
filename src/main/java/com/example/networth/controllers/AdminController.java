package com.example.networth.controllers;

import com.example.networth.models.User;
import com.example.networth.services.RoleService;
import com.example.networth.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController
{
 private final  UserService userService;

    private final RoleService roleService;
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/getUsers")
    public String getUsers(Model model)
    {

        model.addAttribute("users",userService.findAll());
        return "roles/allUsers";
    }


    @GetMapping("/userRoles/edit/{id}")
    public String editUser(@PathVariable Integer id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
        return "roles/userEdit";
    }


    @RequestMapping("/role/assign/{userId}/{roleId}")
    public String assignRole(@PathVariable Integer userId,
                             @PathVariable Integer roleId){
        roleService.assignUserRole(userId, roleId);
        return "redirect:/userRoles/edit/"+userId;
    }


    @RequestMapping("/role/unassign/{userId}/{roleId}")
    public String unassignRole(@PathVariable Integer userId,
                               @PathVariable Integer roleId){
        roleService.unassignUserRole(userId, roleId);
        return "redirect:/userRoles/edit/"+userId;
    }



}
