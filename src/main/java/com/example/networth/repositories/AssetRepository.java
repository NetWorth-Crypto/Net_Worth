package com.example.networth.repositories;


import com.example.networth.models.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset,Long> {
    public Asset findByName(String name);
}
