package com.example.networth.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PortfolioAsset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    Asset asset;

    @Column(nullable = false)
    double quantity;

    @Column(nullable = false)
    double purchasePrice;

    @Temporal(value = TemporalType.DATE)
    @Column(nullable = false)
    Date purchase_date;

    public PortfolioAsset() {
    }

    public PortfolioAsset(Portfolio portfolio, Asset asset, double quantity, double purchasePrice, Date purchase_date) {
        this.portfolio = portfolio;
        this.asset = asset;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.purchase_date = purchase_date;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Override
    public String toString() {
        return "PortfolioAsset{" +
                "id=" + id +
                ", portfolio=" + portfolio +
                ", asset=" + asset +
                ", quantity=" + quantity +
                ", purchasePrice=" + purchasePrice +
                ", purchase_date=" + purchase_date +
                '}';
    }
}
