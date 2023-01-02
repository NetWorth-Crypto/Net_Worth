package com.example.networth.controllers;


import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.models.User;
import com.example.networth.repositories.AssetRepository;
import com.example.networth.repositories.UserRepository;
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
    private final UserRepository userRepository;


    public CrytoController(PortfolioService portfolioService, AssetService assetService, PortfolioAssetService pAservice,
                           AssetRepository assetRepository,
                           UserRepository userRepository) {
        this.portfolioService = portfolioService;
        this.assetService = assetService;
        this.pAservice = pAservice;

        this.userRepository = userRepository;
    }


    //**************************Login User****************************************
    public User logedinUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    //    **************************Get all portfolios that belong to a user******************************************
    List<Portfolio> getAlluserPortfolios(User user) {
        return portfolioService.findByUser(user);
    }


//    **************************Get All Assets that belong in All portfolios of a user*********************************************

    public List<PortfolioAsset> getAllPortfolioAssets(User user) {
        List<Portfolio> portfolios = getAlluserPortfolios(user);
        List<PortfolioAsset> portfolioAssets = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            List<PortfolioAsset> portfolioAsset = pAservice.findByPortfolio(portfolio);
            portfolioAssets.addAll(portfolioAsset);
        }
        return portfolioAssets;
    }


    //    ************************Get All Income In All of a users Portfolios********************************************
    public double AllDollarLimit(User user) {
        List<Portfolio> portfolios = getAlluserPortfolios(user);
        double total = 0;
        for (Portfolio portfolio : portfolios) {
            total += portfolio.getDollarLimit();
        }
        return total;
    }


    //  *******************************Get Available Portfolio Balance****************************************
    public double getPortfolioBallance(long id) {
        Portfolio portfolio = portfolioService.findById(id);
        double initialBalance = portfolio.getDollarLimit();
        List<PortfolioAsset> portfolioAssets = pAservice.findByPortfolio(portfolio);

        double total = 0;
        for (PortfolioAsset portfolioAsset : portfolioAssets) {
            total += portfolioAsset.getQuantity() * portfolioAsset.getPurchasePrice();
        }
        return initialBalance - total;
    }


    //*****************************All income invested****************************************
    public double getAllinvested(User user) {
        double total = 0;
        List<PortfolioAsset> portfolioAssets = getAllPortfolioAssets(user);
        for (PortfolioAsset portfolioAsset : portfolioAssets) {
            total += portfolioAsset.getQuantity() * portfolioAsset.getPurchasePrice();
        }
        return total;
    }


    @GetMapping("/crypto")
    public String getCrypto() {
        return "crypto";
    }


    @GetMapping(path = "/addCrypto/{price}/{name}/{ticker}")
    public String addCrypto(@PathVariable String name, @PathVariable String ticker, @PathVariable float price, Model model, RedirectAttributes redirectAttrs) {


        model.addAttribute("price", price);
        model.addAttribute("name", name);
        model.addAttribute("ticker", ticker);

        User user = logedinUser();
        System.out.println(user);
        List<Portfolio> portfolios = portfolioService.findByUser(user);
        model.addAttribute("portfolios", portfolios);
        if (portfolios.isEmpty()) {
            redirectAttrs.addFlashAttribute("needPortfolio", "Creat a portfolio in order to add assets");
            return "redirect:/createPortfolio";
        }

        return "portfolio/addAsset";
    }

    @RequestMapping(value = "/add_crypto", method = RequestMethod.POST)
    public String addAsset(@RequestParam("name") String name,
                           @RequestParam("ticker") String ticker,
                           @RequestParam("price") double price,
                           @RequestParam("quantity") int quantity,
                           @RequestParam("portfolio") long portfolio,
                           Model model,
                           RedirectAttributes redirectAttrs) {

        Portfolio newPortfolio = portfolioService.findById(portfolio);
        double availableBalance = getPortfolioBallance(newPortfolio.getId());
        double totalPrice = price * quantity;
        Asset newAsset = new Asset(ticker, name, price);

        if (assetService.findByName(name) == null) {
            assetService.addAsset(newAsset);

        } else {
            Asset asset = assetService.findByName(name);
            asset.setCurrentPrice(price);
            assetService.addAsset(asset);
        }
        if (pAservice.findByAssetAndPortfolio(assetService.findByName(name), newPortfolio) != null) {
            redirectAttrs.addFlashAttribute("red", "Ticker already Exist in Portfolio");

            return "redirect:/crypto";
        }

        List<Portfolio> portfolios = portfolioService.findByUser(logedinUser());
        if (totalPrice > availableBalance) {
            model.addAttribute("lowBalance", "your available balance is not enough for " + quantity + " " + name);
            model.addAttribute("price", price);
            model.addAttribute("name", name);
            model.addAttribute("ticker", ticker);
            model.addAttribute("portfolios", portfolios);

            return "portfolio/addAsset";
        }

        Asset asset = assetService.findByName(name);

        Portfolio portfolio1 = portfolioService.findById(portfolio);


        Date date = new Date();
        pAservice.addpAsset(new PortfolioAsset(portfolio1, asset, quantity, price, date));
        redirectAttrs.addFlashAttribute("added", quantity + " " + name + " has been added to " + portfolio1.getName());
        return "redirect:/crypto";

    }

}






