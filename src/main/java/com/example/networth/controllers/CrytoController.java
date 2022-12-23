package com.example.networth.controllers;


import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.models.User;
import com.example.networth.repositories.AssetRepository;
import com.example.networth.services.AssetService;
import com.example.networth.services.PortfolioAssetService;
import com.example.networth.services.PortfolioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CrytoController {


    private final PortfolioService portfolioService;
    private final AssetService assetService;
    private final PortfolioAssetService pAservice;




//    ************************FUNCTION GETS SESSION USER *******************************************
    public  User loginUser(){
       return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


//    ********************Function to get total dollar limit of a user**********************************************
    public double totalDollarLimit(User user){
        double total = 0;
        List<Portfolio> portfolios = portfolioService.findByUser(user);
        for(Portfolio portfolio: portfolios){
            total += portfolio.getDollarLimit();
        }
        return total;
    }



//    **************************Function to get total invested by a user***************************************
    public double totalInvested(User user){

        List<Portfolio> portfolios = portfolioService.findByUser(user);
        double total = 0;
        for(Portfolio portfolio: portfolios){
         total += portfolioBalance(portfolio);
        }
return total;
    }



//    *******************************THIS FUNCTION CHECKS AVAILABLE BALANCE IN A PORTFOLIO***********************************

    public double portfolioBalance(Portfolio portfolio){
        List<PortfolioAsset>portfolioAssets = pAservice.findByPortfolio(portfolio);
        double dollarLimit = portfolio.getDollarLimit();
      double total = 0;
      for(PortfolioAsset portfolioAsset: portfolioAssets){
       double product = portfolioAsset.getPurchasePrice()*portfolioAsset.getQuantity();
       total += product;
      }
      return dollarLimit-total;
    }




    public CrytoController(PortfolioService portfolioService, AssetService assetService, PortfolioAssetService pAservice,
                           AssetRepository assetRepository) {
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



        model.addAttribute("price",price);
        model.addAttribute("name",name);
        model.addAttribute("ticker",ticker);


        System.out.println(loginUser());
        List<Portfolio> portfolios =portfolioService.findByUser(loginUser());
        model.addAttribute("portfolios",portfolios);
if(portfolios.isEmpty()){
   redirectAttrs.addFlashAttribute("needPortfolio","Creat a portfolio in order to add assets");
   return "redirect:/createPortfolio";
}

        return "portfolio/addAsset";
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
        double availableBalance = portfolioBalance(newPortfolio);
        double totalPrice = price*quantity;
        Asset newAsset = new Asset(ticker,name,price);

        if (assetService.findByName(name) == null ) {
            assetService.addAsset(newAsset);

        }
        else
        {
            Asset asset = assetService.findByName(name);
            asset.setCurrentPrice(price);
            assetService.addAsset(asset);
        }
        if(pAservice.findByAssetAndPortfolio(assetService.findByName(name),newPortfolio) != null){
            redirectAttrs.addFlashAttribute("red", "Ticker already Exist in Portfolio");

            return "redirect:/crypto";
        }


        List<Portfolio> portfolios =portfolioService.findByUser(loginUser());
        if(totalPrice>availableBalance){
            model.addAttribute("lowBalance","your available balance is not enough for "+quantity+" "+name );
            model.addAttribute("price",price);
            model.addAttribute("name",name);
            model.addAttribute("ticker",ticker);
            model.addAttribute("portfolios",portfolios);

return "portfolio/addAsset";
        }

            Asset asset = assetService.findByName(name);

            Portfolio portfolio1 = portfolioService.findById(portfolio);


            Date date = new Date();
            pAservice.addpAsset(new PortfolioAsset(portfolio1, asset, quantity, price, date));
redirectAttrs.addFlashAttribute("added",quantity+" "+ name+" has been added to "+ portfolio1.getName());



            return "redirect:/crypto";

        }

        }






