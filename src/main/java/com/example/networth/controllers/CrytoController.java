package com.example.networth.controllers;


import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.models.User;
import com.example.networth.services.AssetService;
import com.example.networth.services.PortfolioAssetService;
import com.example.networth.services.PortfolioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

@Controller
public class CrytoController {


    private final PortfolioService portfolioService;
    private final AssetService assetService;
    private final PortfolioAssetService pAservice;

    public CrytoController(PortfolioService portfolioService, AssetService assetService, PortfolioAssetService pAservice) {
        this.portfolioService = portfolioService;
        this.assetService = assetService;
        this.pAservice = pAservice;
    }


    @GetMapping("/crypto")
    public String getCrypto(){
        return "crypto";
    }

    @GetMapping(path = "/addCrypto/{price}/{name}/{ticker}")
    public String addCrypto(@PathVariable String name,@PathVariable String ticker,@PathVariable float price, Model model,RedirectAttributes redirectAttrs){

       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal()=="anonymousUser"){
    redirectAttrs.addFlashAttribute("login", "login.html To access Portfolio");
    return "redirect:/login";}

        model.addAttribute("price",price);
        model.addAttribute("name",name);
        model.addAttribute("ticker",ticker);

        User user = (User)auth.getPrincipal();
        System.out.println(user);
        List<Portfolio> portfolios =portfolioService.findByUser(user);
        model.addAttribute("portfolios",portfolios);
if(portfolios.isEmpty()){
   redirectAttrs.addFlashAttribute("needPortfolio","Creat a portfolio in order to add assets");
   return "redirect:/createPortfolio";
}

        return "addAsset";
    }

    @RequestMapping(value = "/add_crypto", method = RequestMethod.POST)
    public String addAsset(@RequestParam("name")String name,
                           @RequestParam("ticker")String ticker,
                           @RequestParam("price")double price,
                           @RequestParam("quantity")int quantity,
                           @RequestParam("portfolio")long portfolio,
                              Model model,
    RedirectAttributes redirectAttrs) {

        Portfolio newPortfolio = portfolioService.findById(portfolio);
        double availableBalance = newPortfolio.getDollarLimit();
        double totalPrice = price*quantity;

        if (assetService.findByName(name) != null || pAservice.findByAssetAndPortfolio(assetService.findByName(name),newPortfolio) != null) {
            redirectAttrs.addFlashAttribute("red", "Ticker already Exist in Portfolio");
            return "redirect:/crypto";
        }
        if(totalPrice>availableBalance){
            model.addAttribute("lowBalance","your available balance is not enough for "+quantity+" "+name );
            model.addAttribute("price",price);
            model.addAttribute("name",name);
            model.addAttribute("ticker",ticker);
return "addAsset";
        }

            assetService.addAsset(new Asset(ticker, name, price));
            Asset asset = assetService.findByName(name);

            Portfolio portfolio1 = portfolioService.findById(portfolio);


            Date date = new Date();
            pAservice.addpAsset(new PortfolioAsset(portfolio1, asset, quantity, price, date));

            return "redirect:/userFinance";

        }

        }






