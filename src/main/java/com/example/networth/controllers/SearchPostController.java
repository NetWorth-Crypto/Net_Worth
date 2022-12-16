package com.example.networth.controllers;

import com.example.networth.models.Post;
import com.example.networth.services.SearchPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.FlowView;
import java.util.List;

@Controller
public class SearchPostController {

    @Autowired
    private SearchPostService searchPostService;

    @GetMapping("/searchPost")
    public String showSearch() {
        return "post/searchPost";
    }


    @RequestMapping(path = {"/post/searchPost"})
    public String search(Model model, @RequestParam(value = "keyword") String keyword) {
        System.out.println(keyword);

        List<Post> lists = searchPostService.getByKeyword(keyword);
        model.addAttribute("lists", lists);
        System.out.println(lists);
        return "post/searchResult";

//    @PostMapping("/searchPost")
//    public String postSearch(@RequestParam(value = "keyword")String keyword) {
//        System.out.println(keyword);
//        return "post/searchResult";
//    }
    }
}
