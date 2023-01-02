package com.example.networth.services;

import com.example.networth.models.Asset;
import com.example.networth.repositories.AssetRepository;
import org.springframework.stereotype.Service;

@Service
public class AssetService {
    public final AssetRepository assetDao;

    public AssetService(AssetRepository assetDao) {
        this.assetDao = assetDao;
    }

    public void addAsset(Asset asset){
        assetDao.save(asset);
    }
    public Asset findByName(String name){
     return assetDao.findByName(name);
    }

    public Asset findById(long id){
        return assetDao.findById(id).get();
    }

    public void delete(Asset asset) {
        assetDao.delete(asset);
    }

    Asset getAssetDetails(Asset asset){
        return assetDao.getAssetDetails(asset.getId());

    }
}
