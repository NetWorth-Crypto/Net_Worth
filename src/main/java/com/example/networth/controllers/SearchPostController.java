

package com.example.networth.controllers;

import com.example.networth.models.Post;
import com.example.networth.services.SearchPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchPostController {
    @Autowired
    private SearchPostService searchPostService;

    @RequestMapping(value = {"/searchPost", "/searchPost/{keyword}"})
    public String searchPost(Model model, @RequestParam("keyword")
    Optional<String> keyword) {
        if (keyword.isEmpty()) {
            return "post/searchPost";
        } else {
            String postDetail = keyword.get();
            System.out.println(keyword);
            List<Post> lists = searchPostService.getByKeyword(postDetail);
            model.addAttribute("lists", lists);
            System.out.println(lists);
            return "post/searchPost";
        }
    }
}