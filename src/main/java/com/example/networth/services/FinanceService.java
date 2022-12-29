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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public void getSevenDayPrice() throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("https://api.coingecko.com/api/v3/coins/bitcoin/market_chart?vs_currency=usd&days=7&interval=daily", String.class);

        Map<Long,Double>dayTotalPrice = new HashMap<>();

        JSONObject obj = getJsonObject(result);
        JSONArray days = getJsonArray(obj.get("prices").toString());

        JSONArray day = getJsonArray(days.get(0).toString());

        for(Object currentDay: days){
            JSONArray dayArray = (JSONArray) currentDay;
            Long unixTime = (Long) dayArray.get(0);
            Double coinPrice = (Double) dayArray.get(1);

            System.out.println( dayArray.get(0));
//            System.out.println(dayArray.get(1));
            dayTotalPrice.put(unixTime,coinPrice);
        }

        //Get previous price
            //Day of price action(iterate through days)
                //Get price for that day (API)(done)
                //Use getTotalPrice function

        //Get portfolio total amount for each day
            //past seven day
            //Creat map<day,portfolioTotal>
                //iterate each day to get portfolio total
                //PUT day and total in map
        //return map


    }

    //Function return asset price on passed in UNIX timestamp (date parameter)
    public double assetPriceForDay(String coinName, String date) throws ParseException {
        coinName = coinName.toLowerCase();

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject("https://api.coingecko.com/api/v3/coins/"+coinName+"/history?date="+date,String.class);

        JSONObject resultObj = getJsonObject(result);
        JSONObject marketData = getJsonObject(resultObj.get("market_data").toString());
        JSONObject currentPrice = getJsonObject(marketData.get("current_price").toString());
//        System.out.println(currentPrice.get("usd"));

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
