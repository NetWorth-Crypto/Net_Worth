package com.example.networth.controllers;

import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.models.User;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

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

//    @Autowired
//    PortfolioAssetRepository paDao;

    @Autowired
    PortfolioRepository portfolioDao;

    @GetMapping("/finance")
    public String finance(Model model) throws ParseException {
        User user = userDao.getReferenceById(1l);
        List<Portfolio> portfolios = user.getPortfolios();
        Portfolio selectedPortfolio = portfolios.get(0);

        Map<String,Double> performance = financeService.getSevenDayPrice(selectedPortfolio);

        //Get todays date
        long todayUnixTime = Instant.now().getEpochSecond() * 1000;
        String today = financeService.getDate(todayUnixTime);
        System.out.println("Today's data is: "+ today);

        //Get current portfolio total
        double totalBalance = performance.get(today);

        model.addAttribute("user",user);
        model.addAttribute("portfolios",portfolios);
        model.addAttribute("selectedPortfolio",selectedPortfolio);
        model.addAttribute("totalBalance",totalBalance);

        //Seven day performance of portfolio
        model.addAttribute("portfolioPerformance", performance);

        for (Map.Entry<String,Double> entry: performance.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue() );
        }

        return "financePage";
    }

    @PostMapping("/finance/portfolioSelection")
    public String portfolioSelection(@RequestParam("portfolioId") long portfolioId,
                                     Model model) throws ParseException {

        //Get portfolio data
        Portfolio selectedPortfolio = portfolioDao.getReferenceById(portfolioId);

        //Get User data
        User user = userDao.getReferenceById(1l);

        //Get user's portfolios
        List<Portfolio> portfolios = user.getPortfolios();


        Map<String,Double> performance = financeService.getSevenDayPrice(selectedPortfolio);

        //Get todays date
        long todayUnixTime = Instant.now().getEpochSecond() * 1000;
        String today = financeService.getDate(todayUnixTime);
        System.out.println("Today's data is: "+ today);

        //Get current portfolio total
        double totalBalance = performance.get(today);





        model.addAttribute("selectedPortfolio",selectedPortfolio);
        model.addAttribute("user",user);
        model.addAttribute("portfolios",portfolios);

        //Total Balance of current portfolio
        model.addAttribute("totalBalance", totalBalance);

        //Seven day performance of portfolio
        model.addAttribute("portfolioPerformance", performance);


        return "financePage";
    }


}
