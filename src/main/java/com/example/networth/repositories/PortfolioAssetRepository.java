package com.example.networth.repositories;

import com.example.networth.models.Asset;
import com.example.networth.models.Portfolio;
import com.example.networth.models.PortfolioAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface PortfolioAssetRepository extends JpaRepository<PortfolioAsset,Long> {
    List<PortfolioAsset> findByPortfolio(Portfolio portfolio);

    PortfolioAsset findByAssetAndPortfolio(Asset asset, Portfolio portfolio);

    PortfolioAsset findByAsset(Asset asset);

    List<PortfolioAsset> findAllAssetsByPortfolio(Portfolio portfolio);

    PortfolioAsset findIdByAssetAndPortfolio(Asset asset,Portfolio portfolio);



    @Query(
            value = "select a.name as name, a.ticker as ticker, pa.purchase_price as price, pa.quantity as quantity from asset as a right join portfolio_asset pa on a.id = pa.asset_id", nativeQuery = true
    )
    public  Asset getAssetDetails(@Param("a.id")long id);

}


