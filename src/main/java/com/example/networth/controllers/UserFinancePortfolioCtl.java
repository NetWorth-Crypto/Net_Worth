package com.example.networth.controllers;

import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.models.User;
import com.example.networth.repositories.PortfolioAssetRepository;
import com.example.networth.repositories.PortfolioRepository;
import com.example.networth.repositories.UserRepository;
import com.example.networth.services.AssetService;
import com.example.networth.services.PortfolioAssetService;
import com.example.networth.services.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Objects;


@Controller
public class UserFinancePortfolioCtl {
    private final PortfolioService portfolioService;
    private final AssetService assetService;
    private final PortfolioAssetService portAssetDao;
    private final PortfolioRepository portfolioRepository;

    @Autowired
    PortfolioAssetRepository portfolioAssetDao;

    @Autowired
    UserRepository userDao;

    public UserFinancePortfolioCtl(PortfolioService portfolioService, PortfolioAssetService portAssetDao, AssetService assetService,
                                   PortfolioRepository portfolioRepository) {
        this.portfolioService = portfolioService;
        this.portAssetDao = portAssetDao;
        this.assetService = assetService;
        this.portfolioRepository = portfolioRepository;
    }


    //  *******************************Get Available Portfolio Balance****************************************
    public double getPortfolioBallance(long id) {
        Portfolio portfolio = portfolioService.findById(id);
        double initialBalance = portfolio.getDollarLimit();
        List<PortfolioAsset> portfolioAssets = portAssetDao.findByPortfolio(portfolio);

        double total = 0;
        for (PortfolioAsset portfolioAsset : portfolioAssets) {
            total += portfolioAsset.getQuantity() * portfolioAsset.getPurchasePrice();
        }
        return initialBalance - total;
    }


    public User logedinUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    //    **************************Get all portfolios that belong to a user******************************************
    List<Portfolio> getAlluserPortfolios(User user) {
        return portfolioService.findByUser(user);
    }


//    **************************Get All Assets that belong in All portfolios of a user*********************************************

    public List<PortfolioAsset> getAllPortfolioAssets(User user) {

        List<PortfolioAsset> portfolioAssets = new ArrayList<>();
        for (Portfolio portfolio : getAlluserPortfolios(user)) {
            List<PortfolioAsset> portfolioAsset = portAssetDao.findByPortfolio(portfolio);
            portfolioAssets.addAll(portfolioAsset);
        }
        return portfolioAssets;
    }


    //****************************VIEW USERFINANCE PAGE IF LOGIN************************************
    @GetMapping("/userFinance")
    public String userFinancePage(Model model, RedirectAttributes redirectAttrs) {

        for (Portfolio portfolio : getAlluserPortfolios(logedinUser())) {
            double balance = getPortfolioBallance(portfolio.getId());
            portfolio.setAvailableBalance(balance);
            portfolioService.addPortfolio(portfolio);
        }


        User user = logedinUser();
        System.out.println(user);


        model.addAttribute("portfolios", getAlluserPortfolios(user));


        return "users/userFinance";
    }


//    **************************CREATE PORTFOLIO**************************************

    @GetMapping("/createPortfolio")
    public String createPortfolio() {
        return "portfolio/createPortfolio";
    }

    @PostMapping("/addPortfolio")
    public String addPortfolio(@RequestParam("name") String name,
                               @RequestParam("dollarLimit") int dollarLimit,
                               @RequestParam("type") String type,
                               Model model,
                               RedirectAttributes attributes
    ) {
        boolean isDefault = type.equals("Default");
        boolean isPrivate = type.equals("Private");

        List<Portfolio> portfolios = getAlluserPortfolios(logedinUser());
        for (Portfolio portfolio : portfolios) {
            if (portfolio.getName().equals(name)) {
                attributes.addFlashAttribute("rename", "A portfolio with this name already exist");
                return "redirect:/createPortfolio";
            }
        }
        Portfolio portfolio = new Portfolio(logedinUser(), name, isDefault, dollarLimit, isPrivate, dollarLimit);
        portfolioService.addPortfolio(portfolio);

        return "redirect:/finance";
    }


    //    ******************************Edit Portfolio***************************************
    @GetMapping("/editPortfolio/{id}")
    public String editPortfolio(@PathVariable int id, Model model) {
        Portfolio portfolio = portfolioService.findById(id);
        model.addAttribute("id", id);
        model.addAttribute("name", portfolio.getName());
        model.addAttribute("dollarLimit", portfolio.getDollarLimit());

        return "portfolio/editPortfolio";
    }

    @PostMapping("/saveEdit")
    public String saveEdit(@RequestParam("name") String name,
                           @RequestParam("dollarLimit") double dollarLimit,
                           @RequestParam("id") long id,
                           @RequestParam("type") String type,
                           Model model,
                           RedirectAttributes attributes) {


        Portfolio portfolio = portfolioService.findById(id);
        if (portfolioService.findByNameAndUser(name, logedinUser()) != null && !Objects.equals(name, portfolio.getName())) {
            model.addAttribute("exist", "you already have a portfolio with this name");
            model.addAttribute("id", id);
            model.addAttribute("name", portfolio.getName());
            model.addAttribute("dollarLimit", portfolio.getDollarLimit());

            return "portfolio/editPortfolio";
        }
        if (type.equals("Private")) {
            portfolio.setPrivate(true);
        } else {
            portfolio.setDefault(true);
        }
        portfolio.setName(name);
        portfolio.setDollarLimit(dollarLimit);
        portfolioService.addPortfolio(portfolio);

        Portfolio portfolio1 = portfolioService.findById(id);
        double balance = getPortfolioBallance(id);
        portfolio1.setAvailableBalance(balance);
        portfolioService.addPortfolio(portfolio1);


        return "redirect:/finance";
    }


//    ***************************Delete Portfolio and all its assets****************************************

    @GetMapping("/deletePortfolio/{id}")
    public String deletePortfolio(@PathVariable long id, Model model) {


        System.out.println(id);
        Portfolio portfolio = portfolioService.findById(id);


        for (PortfolioAsset portfolioAsset : portAssetDao.findByPortfolio(portfolio)) {
            portAssetDao.delete(portfolioAsset);
        }
        portfolioService.delete(portfolio);


        return "redirect:/finance";
    }


    //    *****************************VIW PORTFOLIO ASSET IN Single Portfolio*********************************
    @GetMapping(path = "/asset/{id}")
    public String getAsset(@PathVariable long id, Model model) {
        System.out.println(id);
        Portfolio portfolio = portfolioService.findById(id);
        System.out.println(portfolio.getName());
        List<PortfolioAsset> portfolioAssets = portAssetDao.findByPortfolio(portfolio);
        model.addAttribute("portfolioAssets", portfolioAssets);


        List<Asset> assets = new ArrayList<>();
        for (PortfolioAsset portfolioAsset : portfolioAssets) {
            Asset asset = assetService.findById(portfolioAsset.getAsset().getId());
            assets.add(asset);

        }
        model.addAttribute("assets", assets);


        return "portfolio/viewAssets";
    }


//    VIEW ALL ASSET that belongs to a user*************************************************

    @GetMapping("/viewAll")
    public String viewAll(Model model) {


        List<PortfolioAsset> total = new ArrayList<>();
        for (Portfolio portfolio : getAlluserPortfolios(logedinUser())) {
            List<PortfolioAsset> portfolioAssets = portAssetDao.findByPortfolio(portfolio);
            total.addAll(portfolioAssets);
        }

        List<Asset> assets = new ArrayList<>();
        for (PortfolioAsset portfolioAsset : total) {
            Asset asset = assetService.findById(portfolioAsset.getAsset().getId());
            assets.add(asset);

        }

        model.addAttribute("portfolioAssets", total);

        model.addAttribute("assets", assets);
        return "portfolio/viewAssets";
    }


//    Dellete Asset**********************************************


    @GetMapping("/deleteAsset/{id}")
    public String deleteAsset(@PathVariable long id, RedirectAttributes attributes) {
        Asset asset = assetService.findById(id);
        System.out.println(asset.toString());
        attributes.addFlashAttribute("delete", asset.getName() + "Has been deleted from your Portfolio");


        PortfolioAsset portfolioAsset = portAssetDao.findByAsset(asset);
        Portfolio portfolio = portfolioAsset.getPortfolio();
        System.out.println(portfolioAsset.toString());
        portAssetDao.delete(portfolioAsset);

        return "redirect:/viewAll";
    }

    @GetMapping("/asset/deleteAsset/{id}")
    public String deleteAssetFromPortfolio(@PathVariable long id, RedirectAttributes attributes) {
        Asset asset = assetService.findById(id);
        System.out.println(asset.toString());
        attributes.addFlashAttribute("delete", asset.getName() + " has been deleted from your Portfolio");

        PortfolioAsset portfolioAsset = portAssetDao.findByAsset(asset);

        System.out.println(portfolioAsset.toString());

        System.out.println(portfolioAsset.toString());
        portAssetDao.delete(portfolioAsset);


        return "redirect:/viewAll";
    }


    @GetMapping("/updateAsset/{id}/{ticker}/{name}/{price}")
    public String updateAsset(@PathVariable long id,
                              @PathVariable String ticker,
                              @PathVariable String name,
                              @PathVariable float price,
                              Model model) {
        System.out.println(id);
        Asset asset = new Asset(id, ticker, name, price);
        model.addAttribute("asset", asset);
        return "portfolio/updateAsset";
    }

    @GetMapping("/updateAsset/{id}")
    public String updateAsset(@PathVariable long id,
                              Model model) {
        PortfolioAsset asset = portfolioAssetDao.getReferenceById(id);
        model.addAttribute("asset", asset);
        return "portfolio/updateAsset";
    }

    @PostMapping("/updating")
    public String updating(@RequestParam("id") long id,
                           @RequestParam("quantity") double quantity,
                           @RequestParam("price") double price,
                           Model model,
                           RedirectAttributes attributes) {


        PortfolioAsset portfolioAsset = portfolioAssetDao.getReferenceById(id);
//        Asset asset = portfolioAsset.getAsset();
        Portfolio portfolio = portfolioAsset.getPortfolio();
        if (portfolio.getAvailableBalance() < quantity * price) {
            attributes.addFlashAttribute("lowBalance", "Available balance is not enough for the QUANTITY");
//            model.addAttribute("id",asset.getId());
//            model.addAttribute("ticker",asset.getTicker());
//            model.addAttribute("name",asset.getName());
//            model.addAttribute("price",asset.getCurrentPrice());
            model.addAttribute("asset",portfolioAsset);

          return   "redirect: /updateAsset"+portfolioAsset.getId();
        }
        portfolioAsset.setQuantity(quantity);
        portAssetDao.save(portfolioAsset);
        return "redirect:/finance";
    }

}