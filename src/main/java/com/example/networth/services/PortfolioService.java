package com.example.networth.services;


import com.example.networth.models.Portfolio;
import com.example.networth.models.User;
import com.example.networth.repositories.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PortfolioService {

private final PortfolioRepository portfolioDao;


    public PortfolioService(PortfolioRepository portfolioDao) {
        this.portfolioDao = portfolioDao;
    }

    public List<Portfolio> allPortfolio(){
        return portfolioDao.findAll();
    }


    public List<Portfolio> findByUser(User user){
        return portfolioDao.findByUser(user);
    }

    public Portfolio findById(long id){
     Optional<Portfolio>portfolio = portfolioDao.findById(id);
     return portfolio.orElse(null);
    }
public Portfolio findByName(String name){
      return  portfolioDao.findByName(name);
}
public void addPortfolio(Portfolio portfolio){
        portfolioDao.save(portfolio);
}

public Portfolio findByNameAndUser(String name, User user){
  return portfolioDao.findByNameAndUser(name,user);
}

    public void delete(Portfolio portfolio) {
        portfolioDao.delete(portfolio);
    }
}

