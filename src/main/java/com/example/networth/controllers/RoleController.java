package com.example.networth.controllers;


import com.example.networth.models.Role;
import com.example.networth.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/admin/editRole/{id}")
  public String editRole(@PathVariable long id, Model model){
        Role role = roleService.findById(id);
        model.addAttribute("id", role.getId());
        model.addAttribute("type", role.getType());
        model.addAttribute("details", role.getDetails());
        return"roles/editRole";
  }


  @PutMapping("Admin/saveEdited")
  public String saveEdited(Role role){
        roleService.save(role);
     return    "redirect:/roles";
  }

  @RequestMapping("/admin/deleteRole/{id}")
    public String deleteRole(@PathVariable long id){
       Role role = roleService.findById(id);
       roleService.delete(role);
        return"redirect:/roles";
    }

}
