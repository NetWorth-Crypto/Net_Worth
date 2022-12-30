package com.example.networth.controllers;


import com.example.networth.models.Role;
import com.example.networth.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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



    @PostMapping("/addRole")
    public String addRole(Role role){
        roleService.save(role);

        return "redirect:roles";
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
