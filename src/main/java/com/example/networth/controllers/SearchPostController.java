

package com.example.networth.controllers;

import com.example.networth.models.Comment;
import com.example.networth.models.Post;
import com.example.networth.models.User;
import com.example.networth.repositories.CommentRepository;
import com.example.networth.repositories.UserRepository;
import com.example.networth.services.SearchPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchPostController {
    @Autowired
    private SearchPostService searchPostService;

 

    @Autowired
    private CommentRepository commentDao;

    User loggedinUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    @RequestMapping(value = {"/searchPost", "/searchPost/{keyword}"})
    public String searchPosts(Model model, @RequestParam("keyword")
    Optional<String> keyword) {
        if (keyword.isEmpty()) {
            model.addAttribute("user", loggedinUser());
            return "post/searchPost";
        } else {
            String postDetail = keyword.get();
            System.out.println(keyword);
            List<Post> lists = searchPostService.getByKeyword(postDetail);
            model.addAttribute("lists", lists);
            System.out.println(lists);
            model.addAttribute("user", loggedinUser());
            List<Comment> comments = commentDao.findAll();
            model.addAttribute("comments",comments);
            model.addAttribute("newPost",new Post());
            model.addAttribute("newComment", new Comment());

            return "post/searchPost";
        }
    }

}