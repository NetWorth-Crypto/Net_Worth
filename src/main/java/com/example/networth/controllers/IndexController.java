package com.example.networth.controllers;

import com.example.networth.SecurityConfiguration;
import com.example.networth.models.User;
import com.example.networth.repositories.UserRepository;
import com.example.networth.services.FinanceService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    UserRepository userDao;


    @GetMapping("/")

    public String index(Model model) throws ParseException {



        //Get logged-in User
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "newLandingPage";
        }
        User loggedinUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        model.addAttribute("user",user);

        return "newLandingPage";
    }

    @GetMapping("/new")
    public String newIndex(){
        return "newLandingPage";
    }



}
