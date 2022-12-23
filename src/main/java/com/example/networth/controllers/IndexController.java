package com.example.networth.controllers;

import com.example.networth.services.AssetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

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

}
