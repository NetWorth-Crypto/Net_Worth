package com.example.networth.controllers;


import com.example.networth.models.Role;
import com.example.networth.models.User;
import com.example.networth.services.RoleService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RoleController {


    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    public User logedinUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @GetMapping("/admin/roles")
    public String roles(Model model){

        User user = logedinUser();

        List<Role> roles = user.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);


        model.addAttribute("roles",roleService.findAll());
        return "roles/role";
    }



    @PostMapping("/super-admin/addRole")
    public String addRole(Role role, RedirectAttributes attributes){
        if(roleService.findByType(role.getType())!= null){
            attributes.addFlashAttribute("exist","This role already exist");
            return "redirect:/admin/roles";
        }
     ;

        roleService.save(role);

        return "redirect:/admin/roles";
    }


    @GetMapping("/super-admin/editRole/{id}")
  public String editRole(@PathVariable long id, Model model){
        Role role = roleService.findById(id);
        model.addAttribute("id", role.getId());
        model.addAttribute("type", role.getType());
        model.addAttribute("details", role.getDetails());
        return"roles/editRole";
  }


  @PostMapping("/super-admin/saveEdited")
  public String saveEdited(Role role){
        roleService.save(role);
     return    "redirect:/admin/roles";
  }

  @RequestMapping("/super-admin/deleteRole/{id}")
    public String deleteRole(@PathVariable long id,RedirectAttributes attributes){
       Role role = roleService.findById(id);
       List<User> users = role.getUsers();
       if(users.size()!=0){
         attributes.addFlashAttribute("roleInUse",""+ role.getType() +" can't Be deleted, its currently assigned to other users");
           return"redirect:/admin/roles";
       }

       roleService.delete(role);
        return"redirect:/admin/roles";
    }

}
