package com.example.networth.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(name = "available_balance")
    private Double availableBalance;

    @Column
    private boolean isDefault;

    @Column
    private double dollarLimit;

    @Column
    private boolean isPrivate;

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioAsset> portfolioAssets;

    public Portfolio() {
    }

    public Portfolio(User user, String name, boolean isDefault, double dollarLimit, boolean isPrivate,double availableBalance ) {
        this.user = user;
        this.name = name;
        this.isDefault = isDefault;
        this.dollarLimit = dollarLimit;
        this.isPrivate = isPrivate;
        this.availableBalance = availableBalance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public double getDollarLimit() {
        return dollarLimit;
   }

    public void setDollarLimit(double dollarLimit) {
       this.dollarLimit = dollarLimit;
   }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }
}
