package com.example.networth.controllers;

import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.models.User;
import com.example.networth.repositories.PortfolioAssetRepository;
import com.example.networth.services.AssetService;
import com.example.networth.services.PortfolioAssetService;
import com.example.networth.services.PortfolioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
public class UserFinancePortfolioCtl {
    private final PortfolioService portfolioService;
    private final AssetService assetService;
    private final PortfolioAssetService portAssetDao;

    public UserFinancePortfolioCtl(PortfolioService portfolioService, PortfolioAssetService portAssetDao, AssetService assetService) {
        this.portfolioService = portfolioService;
        this.portAssetDao = portAssetDao;
        this.assetService = assetService;
    }

    //****************************VIEW USERFINANCE PAGE IF LOGIN************************************
    @GetMapping("/userFinance")
    public String userFinancePage(Model model, RedirectAttributes redirectAttrs) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() == "anonymousUser") {
            redirectAttrs.addFlashAttribute("login", "login to continue");
            return "redirect:/login";
        }
        User user = (User) auth.getPrincipal();
        System.out.println(user);
        List<Portfolio> portfolios = portfolioService.findByUser(user);
        model.addAttribute("portfolios", portfolios);
        return "users/userFinance";
    }


//    **************************CREATE PORTFOLIO**************************************

    @GetMapping("/createPortfolio")
    public String createPortfolio() {
        return "createPortfolio";
    }

    @PostMapping("/addPortfolio")
    public String addPortfolio(@RequestParam("name") String name,
                               @RequestParam("dollarLimit") int dollarLimit,
                               @RequestParam("type") String type
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isDefault = type.equals("Default");
        boolean isPrivate = type.equals("Private");
        Portfolio portfolio = new Portfolio((User) auth.getPrincipal(), name, isDefault, dollarLimit, isPrivate);

        portfolioService.addPortfolio(portfolio);
        return "redirect:/userFinance";
    }


    //    *****************************VIW PORTFOLIO ASSET*********************************
    @GetMapping(path = "/asset/{id}")
    public String getAsset(@PathVariable long id, Model model) {
        System.out.println(id);
        Portfolio portfolio = portfolioService.findById(id);
        System.out.println(portfolio.getName());
        List<PortfolioAsset> portfolioAssets = portAssetDao.findByPortfolio(portfolio);
        model.addAttribute("portfolioAssets", portfolioAssets);

        List<Asset> assets = new ArrayList<>();
        for (PortfolioAsset ass : portfolioAssets) {
            Asset asset = assetService.findById(ass.getAsset().getId());
            assets.add(asset);

        }
        model.addAttribute("assets", assets);


        return "viewAssets";
    }


//    VIEW ALL ASSET*************************************************

    @GetMapping("/viewAll")
    public String viewAll(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) auth.getPrincipal();
        List<Portfolio> portfolios = portfolioService.findByUser(currentUser);

        List<PortfolioAsset> total = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            List<PortfolioAsset> portfolioAssets = portAssetDao.findByPortfolio(portfolio);
            total.addAll(portfolioAssets);
        }

        List<Asset> assets = new ArrayList<>();
        for (PortfolioAsset ass : total) {
            Asset asset = assetService.findById(ass.getAsset().getId());
            assets.add(asset);

        }

        model.addAttribute("portfolioAssets", total);

        model.addAttribute("assets", assets);
        return "viewAssets";
    }


//    Dellete Asset**********************************************


    @GetMapping("/deleteAsset/{id}")
    public String deleteAsset(@PathVariable long id, RedirectAttributes attributes){
        Asset asset = assetService.findById(id);
        System.out.println(asset.toString());
        attributes.addFlashAttribute("delete",asset.getName()+"Has been deleted from your Portfolio");

        PortfolioAsset portfolioAsset = portAssetDao.findByAsset(asset);
        System.out.println(portfolioAsset.toString());
        portAssetDao.delete(portfolioAsset);
        assetService.delete(asset);

        return "redirect:/viewAll";
    }

    @GetMapping("/asset/deleteAsset/{id}")
    public String deleteAssetFromPortfolio(@PathVariable long id, RedirectAttributes attributes){
        Asset asset = assetService.findById(id);
        System.out.println(asset.toString());
        attributes.addFlashAttribute("delete",asset.getName()+" has been deleted from your Portfolio");

        PortfolioAsset portfolioAsset = portAssetDao.findByAsset(asset);
        System.out.println(portfolioAsset.toString());
        portAssetDao.delete(portfolioAsset);
        assetService.delete(asset);

        return "redirect:/viewAll";
    }


    @GetMapping("/updateAsset/{id}/{ticker}/{name}/{price}")
    public String updateAsset(@PathVariable long id,
                              @PathVariable String ticker,
                              @PathVariable String name,
                              @PathVariable float price,
                              Model model){
        System.out.println(id);
        Asset asset = new Asset(id,ticker,name,price);
        model.addAttribute("asset",asset);
        return "updateAsset";
    }

    @PostMapping("/updating")
    public String updating(@RequestParam("id")long id,
                           @RequestParam("quantity")int quantity){

        Asset asset = assetService.findById(id);
        PortfolioAsset portfolioAsset = portAssetDao.findByAsset(asset);
        portfolioAsset.setQuantity(quantity);
        portAssetDao.save(portfolioAsset);
        return "redirect:/viewAll";
    }

}