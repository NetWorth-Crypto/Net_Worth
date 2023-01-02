package com.example.networth.controllers;

import com.example.networth.models.Role;
import com.example.networth.models.User;
import com.example.networth.services.RoleService;
import com.example.networth.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class AdminController
{
 private final  UserService userService;



    private final RoleService roleService;
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/admin/getUsers")
    public String getUsers(Model model)
    {

        model.addAttribute("users",userService.findAll());
        return "roles/allUsers";
    }


    @GetMapping("/admin/editRole/{id}")
    public String editUser(@PathVariable Integer id, Model model){
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
        return "roles/userEdit";
    }


    @RequestMapping("/super-admin/role/assign/{userId}/{roleId}")
    public String assignRole(@PathVariable Integer userId,
                             @PathVariable Integer roleId){
        roleService.assignUserRole(userId, roleId);
        return "redirect:/admin/editRole/"+userId;
    }


    @RequestMapping("/super-admin/role/unassign/{userId}/{roleId}")
    public String unassignRole(@PathVariable Integer userId,
                               @PathVariable Integer roleId){
        roleService.unassignUserRole(userId, roleId);
        return "redirect:/admin/editRole/"+userId;
    }



    @RequestMapping(value = "/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable long id){
User user = userService.findById(id);
long userId = user.getId();
List<Role> roles = user.getRoles();
        System.out.println(roles.size());
        for (Role role : roles) {
            System.out.println(role.getId());
            long roleId = role.getId();
            System.out.println(roleId);
            System.out.println(userId);
            roleService.unassignUserRole(roleId, userId);

        }

        return "redirect:/admin/getUsers";

    }




    }




