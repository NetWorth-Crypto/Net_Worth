package com.example.networth.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "asset")
    private List<PortfolioAsset> portfolioAssets;

    @Column(unique = true, nullable = false)
    private String ticker;

    @Column(nullable = false)
    private String name;


    @Column(nullable = false)
    private double currentPrice;

    public Asset() {
    }

    public Asset(String ticker, String name,  double currentPrice) {
        this.ticker = ticker;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public Asset(long id, String ticker, String name, double currentPrice) {
        this.id = id;

        this.ticker = ticker;
        this.name = name;
        this.currentPrice = currentPrice;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public List<PortfolioAsset> getPortfolioAssets() {
        return portfolioAssets;
    }

    public void setPortfolioAssets(List<PortfolioAsset> portfolioAssets) {
        this.portfolioAssets = portfolioAssets;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", currentPrice=" + currentPrice +
                '}';
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }


}
