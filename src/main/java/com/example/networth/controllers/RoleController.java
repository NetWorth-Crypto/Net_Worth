package com.example.networth.controllers;


import com.example.networth.models.Role;
import com.example.networth.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RoleController {


    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/admin/roles")
    public String roles(Model model){
        model.addAttribute("roles",roleService.findAll());
        return "roles/role";
    }



    @PostMapping("/super-admin/addRole")
    public String addRole(Role role, RedirectAttributes attributes){
        if(roleService.findByType(role.getType())!= null){
            attributes.addFlashAttribute("exist","This role already exist");
            return "redirect:/super-admin/roles";
        }
     ;

        roleService.save(role);

        return "redirect:/super-admin/roles";
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
     return    "redirect:/super-admin/roles";
  }

  @RequestMapping("/super-admin/deleteRole/{id}")
    public String deleteRole(@PathVariable long id){
       Role role = roleService.findById(id);
       roleService.delete(role);
        return"redirect:/super-admin/roles";
    }

}
