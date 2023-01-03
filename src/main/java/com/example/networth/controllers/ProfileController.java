package com.example.networth.controllers;

import com.example.networth.models.Comment;
import com.example.networth.models.Post;
import com.example.networth.models.User;
import com.example.networth.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProfileController
{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userDao;

    public ProfileController(PasswordEncoder passwordEncoder, UserRepository userDao)
    {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }



    /****************PRODUCTION MAPPING CODE****************/
    @GetMapping("/userProfile")
    public String userProfile(Model model)
    {
        //Get logged-in user
        User loggedinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        //Get logged-in user's posts
        List<Post> posts = user.getPosts();

        //Add attributes for page
        model.addAttribute("user", user);
        model.addAttribute("posts",posts);
        model.addAttribute("newPost",new Post());
        model.addAttribute("newComment",new Comment());
        return "users/userProfile";
    }

    @GetMapping("/userProfile/{id}")
    public String userProfileOther(@PathVariable long id, Model model){
        //Get user
        User user = userDao.getReferenceById(id);

        //Get Post
        List<Post> posts = user.getPosts();

        model.addAttribute("user",user);
        model.addAttribute("posts", posts);
        model.addAttribute("newComment",new Comment());
        return "users/userProfile";
    }

    /****************TEST MAPPING CODE****************/

    @GetMapping("/profile")
    public String profile(Model model)
    {

        User loggedinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDao.getReferenceById(loggedinUser.getId());

        model.addAttribute("updateProfile", user);
        return "users/userProfile";
    }

    @GetMapping("/updateProfile")
    public String updateProfile(Model model)
    {

        User loggedinUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userDao.getReferenceById(loggedinUser.getId());

        model.addAttribute("user", user);

        return "users/updateProfile";
    }
    @PostMapping("/updateProfile")
    public String updateProfile
            (@ModelAttribute User updatedUser,
             @RequestParam("id") long id,
             @RequestParam("username") String username,
             @RequestParam("firstName") String firstName,
             @RequestParam("lastName") String lastName,
             @RequestParam("email") String email,
             @RequestParam("password") String password)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loggedinUser = (User) authentication.getPrincipal();

        User user = userDao.getReferenceById(loggedinUser.getId());

        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        if (!password.isEmpty())
        {
            String hash = passwordEncoder.encode(password);
            user.setPassword(hash);
        }

        userDao.save(user);


        return "redirect:users/userProfile";
    }

    @PostMapping("/delete")
    public String deleteUser()
    {
        return "redirect:/login";
    }
}
