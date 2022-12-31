package com.example.networth.services;

import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.repositories.PortfolioAssetRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FinanceService {

    @Autowired
    PortfolioAssetRepository paDao;

    public JSONObject getJsonObject(String obj) throws ParseException {
        JSONParser jsonParser = new JSONParser();

        JSONObject jsonObject = (JSONObject) jsonParser.parse(obj);

        return jsonObject;
    }

    public JSONArray getJsonArray(String obj) throws ParseException {
        JSONParser jsonParser = new JSONParser();

        JSONArray jsonArray = (JSONArray) jsonParser.parse(obj);

        return jsonArray;
    }


    /**************Functions for past data**************/
    public SortedMap<String, Double> getSevenDayPrice(Portfolio portfolio) throws ParseException {


        //Map will contain day and portfolio total value for that day
        SortedMap<String,Double> sevenDayPerformance = new TreeMap<>();

        //Get all assets in portfolio
        List<PortfolioAsset> portfolioAssets = paDao.findAllAssetsByPortfolio(portfolio);

        //Portfolio total this iteration
        double portfolioTotal = 0;

        //Get portfolio calculated total
        for(PortfolioAsset portfolioAsset: portfolioAssets){

            //Asset name
            String assetName = portfolioAsset.getAsset().getName();

            //Get quantity
            double assetQuantity = portfolioAsset.getQuantity();

            //Get past seven days of data
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject("https://api.coingecko.com/api/v3/coins/"+assetName+"/market_chart?vs_currency=usd&days=7&interval=daily", String.class);

            for (int i=0; i<8; i++){

                //Parse JSON to get days
                JSONObject obj = getJsonObject(result);
                JSONArray days = getJsonArray(obj.get("prices").toString());

                //Get date
                JSONArray currentDay = (JSONArray) days.get(i);
                Long unixTime = (Long) currentDay.get(0);
                System.out.println("Unix time: "+ unixTime);

                //Get price for day
                Double assetPrice = (Double) currentDay.get(1);

                //Formatted date
                String formattedDate = getDate(unixTime);


                //Get asset total
                double assetTotal = assetPrice * assetQuantity;

                //Put in map
                if(sevenDayPerformance.get(formattedDate) == null){
                    sevenDayPerformance.put(formattedDate,assetTotal);
                }else {
                    //Calculate new total
                    double portfolioTotalForDay = sevenDayPerformance.get(formattedDate) + assetTotal;
                    portfolioTotalForDay = new BigDecimal(portfolioTotalForDay).setScale(2, RoundingMode.HALF_UP).doubleValue();
                    //Put
                    sevenDayPerformance.put(formattedDate,portfolioTotalForDay);
                }

            }

        }

        //Get portfolio total for each day
//        for(Object currentDay: days){
//            JSONArray dayArray = (JSONArray) currentDay;
//            Long unixTime = (Long) dayArray.get(0);
//            System.out.println("Unix time: "+ unixTime);
//
//            //Formatted date
//            String formattedDate = getDate(unixTime);
//
//
//
//            //Assign day and total price to map
//            sevenDayPerformance.put(formattedDate,portfolioTotal);
//
//
//
//        }

        return sevenDayPerformance;

    }

    //Function return asset price on passed in UNIX timestamp (date parameter)
    public double assetPriceForDay(String coinName, String date) throws ParseException {
        coinName = coinName.toLowerCase();

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("https://api.coingecko.com/api/v3/coins/"+coinName+"/history?date="+date,String.class);

        JSONObject resultObj = getJsonObject(result);
        JSONObject marketData = getJsonObject(resultObj.get("market_data").toString());
        JSONObject currentPrice = getJsonObject(marketData.get("current_price").toString());
        System.out.println(currentPrice.get("usd"));

        return (double) currentPrice.get("usd");

    }

    //Function Convert Unix Time regular date
    public String getDate(long unixTime){

        //convert seconds to milliseconds
        Date date = new Date(unixTime);

        // format of the date
        SimpleDateFormat jdf = new SimpleDateFormat("dd-MM-yyyy");

        String java_date = jdf.format(date);
        System.out.println("\n"+java_date+"\n");

        return java_date;
    }

    /**************Functions for current data**************/
    //Function gets asset current price
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

    //Function gets current portfolio total amount
    public double getTotalPrice(Portfolio selectedPortfolio) throws ParseException {
        //Total price of all assets
        double totalPrice = 0;
        ////Get all assets in a portfolio
        List<PortfolioAsset> portfolioAssets = paDao.findAllAssetsByPortfolio(selectedPortfolio);
        for (PortfolioAsset portfolioAsset: portfolioAssets){

//            System.out.println(portfolioAsset.getAsset().getName());
            //Get Current price
            double currentPrice = getPrice(portfolioAsset.getAsset().getName());
//            System.out.println(currentPrice);

            //Get quantity
            double quantity = portfolioAsset.getQuantity();
//            System.out.println(quantity);

            //Get total
            double assetTotal = currentPrice*quantity;
//            System.out.println(assetTotal);
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
