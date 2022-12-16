package com.example.networth.repositories;

import com.example.networth.models.Follower;
import com.example.networth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {

}
