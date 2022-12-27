package com.example.networth.controllers;

import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.models.User;
import com.example.networth.repositories.PortfolioAssetRepository;
import com.example.networth.repositories.PortfolioRepository;
import com.example.networth.repositories.UserRepository;
import com.example.networth.services.FinanceService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


@Controller
public class FinanceController {
    @Autowired
    UserRepository userDao;

    @Autowired
    FinanceService financeService;

//    @Autowired
//    PortfolioAssetRepository paDao;

    @Autowired
    PortfolioRepository portfolioDao;

    @GetMapping("/finance")
    public String finance(Model model){
        User user = userDao.getReferenceById(1l);
        List<Portfolio> portfolios = user.getPortfolios();
        Portfolio selectedPortfolio = portfolios.get(0);

        model.addAttribute("user",user);
        model.addAttribute("portfolios",portfolios);
        model.addAttribute("selectedPortfolio",selectedPortfolio);
        model.addAttribute("totalPrice",0);
        return "financePage";
    }

    @PostMapping("/finance/portfolioSelection")
    public String portfolioSelection(@RequestParam("portfolioId") long portfolioId,
                                     Model model) throws ParseException {


//        FinanceService fs = new FinanceService();

        Portfolio selectedPortfolio = portfolioDao.getReferenceById(portfolioId);
        User user = userDao.getReferenceById(1l);
        List<Portfolio> portfolios = user.getPortfolios();

//        //Total price of all assets
        double totalPrice = 0;
//
        totalPrice = financeService.getTotalPrice(selectedPortfolio);
        System.out.println("Total price: "+totalPrice);




        //Portfolio Action for past 7 days
            //forEach day
                //get portfolioTotal
                //push to array


        model.addAttribute("selectedPortfolio",selectedPortfolio);
        model.addAttribute("user",user);
        model.addAttribute("portfolios",portfolios);

        model.addAttribute("totalPrice",totalPrice);
        return "financePage";
    }


}
