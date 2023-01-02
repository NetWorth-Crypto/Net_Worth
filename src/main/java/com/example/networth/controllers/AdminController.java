package com.example.networth.controllers;

import com.example.networth.models.User;
import com.example.networth.services.RoleService;
import com.example.networth.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public AdminController(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }


    @GetMapping("/admin/getUsers")
    public String getUsers(Model model) {

        model.addAttribute("users", userService.findAll());
        return "roles/allUsers";
    }


    @GetMapping("/admin/editRole/{id}")
    public String editUser(@PathVariable Integer id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", roleService.getUserRoles(user));
        model.addAttribute("userNotRoles", roleService.getUserNotRoles(user));
        return "roles/userEdit";
    }


    @RequestMapping("/super-admin/role/assign/{userId}/{roleId}")
    public String assignRole(@PathVariable Integer userId,
                             @PathVariable Integer roleId) {
        roleService.assignUserRole(userId, roleId);
        return "redirect:/admin/editRole/" + userId;
    }


    @RequestMapping("/super-admin/role/unassign/{userId}/{roleId}")
    public String unassignRole(@PathVariable Integer userId,
                               @PathVariable Integer roleId) {
        roleService.unassignUserRole(userId, roleId);
        return "redirect:/admin/editRole/" + userId;
    }


    @RequestMapping(value = "/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable long id) {
        User user = userService.findById(id);
        long userId = user.getId();
        userService.delete(user);

        return "redirect:/admin/getUsers";

    }


    @RequestMapping(value = "/admin/editUser/{id}")
    public String editUser(@PathVariable long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);

        return "roles/adminEditUser";

    }


    @PostMapping("admin/saveEdit")
    public String confirmEdit(@RequestParam("id") long id,
                              @RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("username") String username,
                              @RequestParam("email") String email,
                              @RequestParam("password") String password) {

        User user = userService.findById(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setEmail(username );
       if(!password.isEmpty()){
           String hash = passwordEncoder.encode(password);
           user.setPassword(hash);

       }
userService.saveUser(user);
        return "redirect:/admin/getUsers";
    }


}




