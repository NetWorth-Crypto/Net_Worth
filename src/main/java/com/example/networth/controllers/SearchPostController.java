

package com.example.networth.controllers;

import com.example.networth.models.Post;
import com.example.networth.services.SearchPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class SearchPostController {
    @Autowired
    private SearchPostService searchPostService;

    @GetMapping("searchPost")
    public String search(Model model, String keyword) {
        System.out.println(keyword);

//        if (keyword != null){
        List<Post> lists = searchPostService.getByKeyword(keyword);
        model.addAttribute("lists", lists);
        System.out.println(lists);
//        }else {
//            List<Post> list = searchPostService.getByKeyword(keyword);
//            model.addAttribute("list", list);}
        return "post/searchPost";
    }
}