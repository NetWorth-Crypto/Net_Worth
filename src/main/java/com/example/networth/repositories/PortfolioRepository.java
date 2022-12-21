package com.example.networth.repositories;

import com.example.networth.models.Portfolio;
import com.example.networth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio,Long> {
    List<Portfolio> findByUser(User user);

   Portfolio findByName(String name);

    Portfolio findByNameAndUser(String name, User user);

    public void delete(Portfolio portfolio);
}
