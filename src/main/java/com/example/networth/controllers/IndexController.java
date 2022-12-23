package com.example.networth.controllers;


import com.example.networth.models.User;
import com.example.networth.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    UserRepository userDao;

  private final AssetService assetService;

    public IndexController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("/")

    public String index(){



        return "landing";
    }

    @GetMapping("/new")
    public String newIndex(){
        return "newLandingPage";
    }

    @GetMapping("/finance")
    public String finance(Model model){
        User user = userDao.getReferenceById(1l);

        model.addAttribute("user",user);
        return "financePage";
    }

}
