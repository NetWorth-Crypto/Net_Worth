package com.example.networth.repositories;

import com.example.networth.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long>
{


}
