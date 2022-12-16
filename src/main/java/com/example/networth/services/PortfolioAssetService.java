package com.example.networth.services;

import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import com.example.networth.repositories.PortfolioAssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PortfolioAssetService {
    public final PortfolioAssetRepository pADao;

    public PortfolioAssetService(PortfolioAssetRepository pADao) {
        this.pADao = pADao;
    }
  public List<PortfolioAsset>findByPortfolio(Portfolio portfolio){
        return pADao.findByPortfolio(portfolio);
    }

    public void addpAsset(PortfolioAsset pAsset){
        pADao.save(pAsset);
    }
    public PortfolioAsset findByAssetAndPortfolio(Asset asset, Portfolio portfolio){
       return pADao.findByAssetAndPortfolio(asset,portfolio);
    }

    public PortfolioAsset findByAsset(Asset asset){
        return pADao.findByAsset(asset);
    }

    public void delete(PortfolioAsset portfolioAsset) {
        pADao.delete(portfolioAsset);
    }

    public void save(PortfolioAsset portfolioAsset) {
        pADao.save(portfolioAsset);
    }
}
