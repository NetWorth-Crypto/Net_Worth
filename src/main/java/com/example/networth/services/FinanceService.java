package com.example.networth.services;

import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.repositories.PortfolioAssetRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class FinanceService {

    @Autowired
    PortfolioAssetRepository paDao;


    public double getPrice(String coinName) throws ParseException {
        coinName = coinName.toLowerCase();

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("https://api.coingecko.com/api/v3/simple/price?ids="+coinName+"&vs_currencies=usd",String.class);

        //String -> Parse to Obj -> Cast to JSONObect -> use operations (get,etc...) ->Cast operation to final type(String,etc.)
        JSONParser jsonParser = new JSONParser();

        //Coin object
        JSONObject coinObj = (JSONObject) jsonParser.parse(result);

        //Price object
        JSONObject priceObj = (JSONObject)jsonParser.parse(coinObj.get(coinName).toString());

        return (double) priceObj.get("usd");

    }

    public double getTotalPrice(Portfolio selectedPortfolio) throws ParseException {
        //Total price of all assets
        double totalPrice = 0;
        ////Get all assets in a portfolio
        List<PortfolioAsset> portfolioAssets = paDao.findAllAssetsByPortfolio(selectedPortfolio);
        for (PortfolioAsset portfolioAsset: portfolioAssets){

            System.out.println(portfolioAsset.getAsset().getName());
            //Get Current price
            double currentPrice = getPrice(portfolioAsset.getAsset().getName());
            System.out.println(currentPrice);

            //Get quantity
            double quantity = portfolioAsset.getQuantity();
            System.out.println(quantity);

            //Get total
            double assetTotal = currentPrice*quantity;
            System.out.println(assetTotal);
            totalPrice += assetTotal;
        }
        totalPrice = new BigDecimal(totalPrice).setScale(2, RoundingMode.HALF_UP).doubleValue();
        return  totalPrice;
    }

//    public double getTotal(){
//
//        for()
//    }

}
