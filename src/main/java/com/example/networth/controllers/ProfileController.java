package com.example.networth.controllers;

import com.example.networth.models.Comment;
import com.example.networth.models.Post;
import com.example.networth.models.User;
import com.example.networth.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProfileController
{
    private final UserRepository userDao;

    public ProfileController(UserRepository userDao)
    {
        this.userDao = userDao;
    }



    /****************PRODUCTION MAPPING CODE****************/
    @GetMapping("/userProfile")
    public String userProfile(Model model)
    {
        //Get logged-in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        //Get logged-in user's posts
        List<Post> posts = user.getPosts();

        //Add attributes for page
        model.addAttribute("user", user);
        model.addAttribute("posts",posts);
        model.addAttribute("newComment",new Comment());
        return "userProfile";
    }

    /****************TEST MAPPING CODE****************/

    @GetMapping("/profile")
    public String profile(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDao.getReferenceById(loggedinUser.getId());

        model.addAttribute("updateProfile", user);
        return "users/profile";
    }

    @GetMapping("/update-profile")
    public String updateProfile(Model model)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDao.getReferenceById(loggedinUser.getId());

        model.addAttribute("updateProfile", user);

        return "users/update-profile";
    }
    @PostMapping("/update")
    public String updateProfile
            (@RequestParam("username")String username,
             @RequestParam("firstname") String firstname,
             @RequestParam("lastname") String lastname,
             @RequestParam("email") String email,
             @RequestParam("password") String password)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedinUser = (User) authentication.getPrincipal();

        User user = userDao.getReferenceById(loggedinUser.getId());


        user.setUsername(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setPassword(password);
        System.out.println(username);
        userDao.save(user);
        System.out.println(user);

        return "redirect:/profile";
    }

    @PostMapping("/delete")
    public String deleteUser()
    {
        return "redirect:/login";
    }
}
