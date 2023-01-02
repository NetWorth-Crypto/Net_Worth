package com.example.networth.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
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
