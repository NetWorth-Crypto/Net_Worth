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

        //Get portfolio data
        Portfolio selectedPortfolio = portfolioDao.getReferenceById(portfolioId);

        //Get User data
        User user = userDao.getReferenceById(1l);

        //Get user's portfolios
        List<Portfolio> portfolios = user.getPortfolios();

        //Total price of all assets
        double totalPrice = 0;
        totalPrice = financeService.getTotalPrice(selectedPortfolio);


        //Portfolio Action for past 7 days
//        RestTemplate restTemplate = new RestTemplate();
//        String result = restTemplate.getForObject("https://api.coingecko.com/api/v3/coins/bitcoin/market_chart?vs_currency=usd&days=7&interval=daily", String.class);
//
//        JSONObject obj = financeService.getJsonObject(result);
//        JSONArray days = financeService.getJsonArray(obj.get("prices").toString());
//
//        JSONArray day = financeService.getJsonArray(days.get(0).toString());
//
//
//
//        System.out.println("Time: "+ (long)day.get(0));
//        System.out.println("Price: "+ (double)day.get(1));

        //Seven day price
            //Get portfolio price per day
                //Get selected portfolio
                //Get Array of totalPrice for seven days(function return array of total price)
                    //Get price data forEach asset
                        //Price data should originate from previous dates
                        //Add price data
        financeService.assetPriceForDay("bitcoin", financeService.getDate(1671212553000l));
//       String newDate = financeService.getDate(1672258827);
//        System.out.println(newDate);

        model.addAttribute("selectedPortfolio",selectedPortfolio);
        model.addAttribute("user",user);
        model.addAttribute("portfolios",portfolios);

        model.addAttribute("totalPrice",totalPrice);
        return "financePage";
    }


}
