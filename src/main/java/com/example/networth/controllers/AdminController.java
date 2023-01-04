package com.example.networth.controllers;

import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.models.Role;
import com.example.networth.models.User;
import com.example.networth.services.PortfolioAssetService;
import com.example.networth.services.PortfolioService;
import com.example.networth.services.RoleService;
import com.example.networth.services.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {
    private final UserService userService;


    private final PortfolioAssetService portfolioAssetService;


    private final PortfolioService portfolioService;


    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public AdminController(UserService userService, PortfolioAssetService portfolioAssetService, PortfolioService portfolioService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.portfolioAssetService = portfolioAssetService;
        this.portfolioService = portfolioService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public User logedinUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @GetMapping("/admin/getUsers")
    public String getUsers(Model model) {
User user = logedinUser();
List<Role> roles = user.getRoles();
        model.addAttribute("users", userService.findAll());
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
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



    public List<PortfolioAsset> getAllPortfolioAssets(User user) {

        List<PortfolioAsset> portfolioAssets = new ArrayList<>();
        for (Portfolio portfolio : getAlluserPortfolios(user)) {
            List<PortfolioAsset> portfolioAsset = portfolioAssetService.findByPortfolio(portfolio);
            portfolioAssets.addAll(portfolioAsset);
        }
        return portfolioAssets;
    }


  public   List<Portfolio> getAlluserPortfolios(User user) {
        return portfolioService.findByUser(user);
    }


    @RequestMapping(value = "/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable long id) {
        User user = userService.findById(id);

        for(Portfolio portfolio:getAlluserPortfolios(user)){

            for (PortfolioAsset portfolioAsset : portfolioAssetService.findByPortfolio(portfolio)) {
                portfolioAssetService.delete(portfolioAsset);
            }
            portfolioService.delete(portfolio);


        }

        userService.delete(user);


        return "redirect:/admin/getUsers";

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
        return "redirect:/admin/editRole/"+id;
    }


}




