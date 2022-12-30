package com.example.networth.repositories;


import com.example.networth.models.Asset;
import com.example.networth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset,Long> {
    public Asset findByName(String name);


@Query(
    value = "select a.name as name, a.ticker as ticker, pa.purchase_price as price, pa.quantity as quantity from asset as a right join portfolio_asset pa on a.id = pa.asset_id", nativeQuery = true
)
 public  Asset getAssetDetails(@Param("a.id")long id);

}
