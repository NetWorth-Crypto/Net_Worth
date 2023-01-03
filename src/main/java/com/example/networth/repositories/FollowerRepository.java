package com.example.networth.repositories;

import com.example.networth.models.Follower;
import com.example.networth.models.Post;
import com.example.networth.models.PostLike;
import com.example.networth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowerRepository extends JpaRepository<Follower, Long> {


}
