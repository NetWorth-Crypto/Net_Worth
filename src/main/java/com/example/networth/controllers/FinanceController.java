package com.example.networth.controllers;

import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.models.User;
import com.example.networth.repositories.AssetRepository;
import com.example.networth.repositories.PortfolioAssetRepository;
import com.example.networth.repositories.PortfolioRepository;
import com.example.networth.repositories.UserRepository;
import com.example.networth.services.FinanceService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class FinanceController {
    @Autowired
    UserRepository userDao;

    @Autowired
    FinanceService financeService;

    @Autowired
    PortfolioAssetRepository paDao;

    @Autowired
    PortfolioRepository portfolioDao;

    @Autowired
    AssetRepository assetDao;

    @GetMapping("/finance")
    public String finance(Model model, RedirectAttributes redirectAttributes) throws ParseException {
        //Get logged-in User
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        //Get all user Portfolios

        List<Portfolio> portfolios = user.getPortfolios();
        if (portfolios.isEmpty()) {
            redirectAttributes.addFlashAttribute("needPortfolio", "Creat a portfolio in order to view financial dashboard page");
            return "redirect:/createPortfolio";
        }
        Portfolio selectedPortfolio = portfolios.get(0);

        Map<String,Double> performance = financeService.getSevenDayPrice(selectedPortfolio);

        //Get todays date
        long todayUnixTime = Instant.now().getEpochSecond() * 1000;
        String today = financeService.getDate(todayUnixTime);
        System.out.println("Today's data is: "+ today);

        //Get previous day date
        long yesterdayUnixTime = (Instant.now().getEpochSecond()-86400) * 1000;
        String yesterday = financeService.getDate(yesterdayUnixTime);
        System.out.println("Yesterday's data is: "+ yesterday);

        //Get current portfolio total
        double totalBalance;
        if(performance.get(today) == null){
            totalBalance = 0;
        }else{
            totalBalance = performance.get(today);
        }

        //Get 24h portfolio change
        double yesterdayTotalBalance;
        if(performance.get(yesterday) == null){
            yesterdayTotalBalance = 0;
        }else{
            yesterdayTotalBalance = performance.get(yesterday);
        }

        double change = yesterdayTotalBalance -totalBalance;
        change = new BigDecimal(change).setScale(2, RoundingMode.HALF_UP).doubleValue();
        System.out.println("Yesterday's balance is: "+ yesterdayTotalBalance);
        System.out.println("total change: "+change);

        //Asset data (price, 24h change, market cap)
        Map<String,Double> portData = financeService.getAssetData(selectedPortfolio);

        model.addAttribute("user",user);
        model.addAttribute("portfolios",portfolios);
        model.addAttribute("selectedPortfolio",selectedPortfolio);
        model.addAttribute("totalBalance",totalBalance);

        //Seven day performance of portfolio
        model.addAttribute("portfolioPerformance", performance);

        //24h Portfolio Change
        model.addAttribute("portfolioChange", change);

        //Asset data (price, 24h change, market cap)
        model.addAttribute("assetData", portData);

        //Assets in portfolio
        model.addAttribute("assets", financeService.getAssets(selectedPortfolio));

        //Assets in portfolio
        model.addAttribute("portfolioAssets", financeService.getPortfolioAssets(selectedPortfolio));


        //Access User's PortfolioAsset for deletion
//        PortfolioAsset portFolioAssetId = paDao.findIdByAssetAndPortfolio(financeService.getAssets(selectedPortfolio).get(1),selectedPortfolio);
//
//        System.out.println("Asset searched for: "+ financeService.getAssets(selectedPortfolio).get(1));
//        System.out.println("Portfolio search for: "+ selectedPortfolio.getId());
//        System.out.println("portfolio asset id: "+portFolioAssetId.getId());
//        for (Map.Entry<String,Double> entry: portData.entrySet()){
//            System.out.println("Key: "+entry.getKey()+" Value: "+entry.getValue());
//        }

        return "financePage";
    }

    @PostMapping("/finance/portfolioSelection")
    public String portfolioSelection(@RequestParam("portfolioId") long portfolioId,
                                     Model model) throws ParseException {

        //Get portfolio data
        Portfolio selectedPortfolio = portfolioDao.getReferenceById(portfolioId);

        //Get logged-in User
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser")
        {
            return "redirect:login";
        }
        User loggedinUser =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getReferenceById(loggedinUser.getId());

        //Get user's portfolios
        List<Portfolio> portfolios = user.getPortfolios();


        Map<String,Double> performance = financeService.getSevenDayPrice(selectedPortfolio);

        //Get todays date
        long todayUnixTime = Instant.now().getEpochSecond() * 1000;
        String today = financeService.getDate(todayUnixTime);
        System.out.println("Today's data is: "+ today);

        //Get previous day date
        long yesterdayUnixTime = (Instant.now().getEpochSecond()-86400) * 1000;
        String yesterday = financeService.getDate(yesterdayUnixTime);
        System.out.println("Yesterday's data is: "+ yesterday);

        //Get current portfolio total for current day
        double totalBalance;
        if(performance.get(today) == null){
            totalBalance = 0;
        }else{
            totalBalance = performance.get(today);
        }
        System.out.println("Today's balance is: "+ totalBalance);

        //Get 24h portfolio change
        double yesterdayTotalBalance;
        if(performance.get(yesterday) == null){
            yesterdayTotalBalance = 0;
        }else{
            yesterdayTotalBalance = performance.get(yesterday);
        }
        double change = yesterdayTotalBalance -totalBalance;
        change = new BigDecimal(change).setScale(2, RoundingMode.HALF_UP).doubleValue();
        System.out.println("Yesterday's balance is: "+ yesterdayTotalBalance);
        System.out.println("total change: "+change);

        //Asset data (price, 24h change, market cap)
        Map<String,Double> portData = financeService.getAssetData(selectedPortfolio);



        model.addAttribute("selectedPortfolio",selectedPortfolio);
        model.addAttribute("user",user);
        model.addAttribute("portfolios",portfolios);

        //Total Balance of current portfolio
        model.addAttribute("totalBalance", totalBalance);

        //Seven day performance of portfolio
        model.addAttribute("portfolioPerformance", performance);

        //24h Portfolio Change
        model.addAttribute("portfolioChange", change);

        //Asset data (price, 24h change, market cap)
        model.addAttribute("assetData", portData);

        //Assets in portfolio
        model.addAttribute("assets", financeService.getAssets(selectedPortfolio));

        //Assets in portfolio
        model.addAttribute("portfolioAssets", financeService.getPortfolioAssets(selectedPortfolio));


        return "financePage";
    }

    @GetMapping("/finance/delete/asset/{id}/{portfolioId}")
    public String deleteAsset(@PathVariable("id") long assetId,
                              @PathVariable("portfolioId") long portfolioId){
        Asset asset = assetDao.getReferenceById(assetId);
        Portfolio portfolio = portfolioDao.getReferenceById(portfolioId);

        PortfolioAsset portfolioAsset = paDao.findIdByAssetAndPortfolio(asset,portfolio);

        paDao.delete(portfolioAsset);

        return "redirect:/finance";
    }


}
