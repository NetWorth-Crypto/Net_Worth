package com.example.networth.repositories;

import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PortfolioAssetRepository extends JpaRepository<PortfolioAsset,Long> {
    List<PortfolioAsset> findByPortfolio(Portfolio portfolio);

    PortfolioAsset findByAssetAndPortfolio(Asset asset, Portfolio portfolio);

    PortfolioAsset findByAsset(Asset asset);

//    @Query(value = "select ")
    List<PortfolioAsset> findAllAssetsByPortfolio(Portfolio portfolio);
}
